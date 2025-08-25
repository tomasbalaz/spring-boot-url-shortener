package sk.balaz.springbooturlshortener.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.balaz.springbooturlshortener.ApplicationProperties;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;
import sk.balaz.springbooturlshortener.domain.models.CreateShortUrlCmd;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.repositories.ShortUrlRepository;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

  private final ShortUrlRepository shortUrlRepository;

  private final EntityMapper entityMapper;

  private final ApplicationProperties properties;

  public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper,
    ApplicationProperties properties) {
    this.shortUrlRepository = shortUrlRepository;
    this.entityMapper = entityMapper;
    this.properties = properties;
  }

  public List<ShortUrlDto> getAllShortUrls() {
    return shortUrlRepository.findPublicShortUrls()
      .stream().map(entityMapper::toShortUrlDto).toList();
  }

  @Transactional
  public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {
    if(properties.validateOriginalUrl()) {
      boolean urlExists = UrlExistenceValidator.isUrlExists(cmd.originalUrl());
      if (!urlExists) {
        throw new RuntimeException("Invalid URL " + cmd.originalUrl());
      }
    }


    var shortUrl = new ShortUrl();
    shortUrl.setOriginalUrl(cmd.originalUrl());
    shortUrl.setShortKey(generateUniqueShortKey());
    shortUrl.setCreatedBy(null);
    shortUrl.setIsPrivate(false);
    shortUrl.setClickCount(0L);
    shortUrl.setExpiresAt(Instant.now().plus(properties.expiryInDays(), ChronoUnit.DAYS));
    shortUrl.setCreatedAt(Instant.now());
    shortUrlRepository.save(shortUrl);
    return entityMapper.toShortUrlDto(shortUrl);
  }

  private String generateUniqueShortKey() {
    String shortKey;
    do {
      shortKey = generateRandomShortKey();
    } while (shortUrlRepository.existsByShortKey(shortKey));
    return shortKey;
  }

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int SHORT_KEY_LENGTH = 6;
  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateRandomShortKey() {
    StringBuilder sb = new StringBuilder(SHORT_KEY_LENGTH);
    for (int i = 0; i < SHORT_KEY_LENGTH; i++) {
      sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }
    return sb.toString();
  }

  public Optional<ShortUrlDto> accessShortUrl(String shortKey) {
    Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByShortKey(shortKey);
    if (shortUrlOptional.isEmpty()) {
      return Optional.empty();
    }
    ShortUrl  shortUrl = shortUrlOptional.get();

    if (shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
      return Optional.empty();
    }
    return shortUrlOptional.map(entityMapper::toShortUrlDto);
  }
}
