package sk.balaz.springbooturlshortener.domain.services;

import org.springframework.stereotype.Service;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.repositories.ShortUrlRepository;

import java.util.List;

@Service
public class ShortUrlService {

  private final ShortUrlRepository shortUrlRepository;

  private final EntityMapper entityMapper;

  public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper) {
    this.shortUrlRepository = shortUrlRepository;
    this.entityMapper = entityMapper;
  }

  public List<ShortUrlDto> getAllShortUrls() {
    return shortUrlRepository.findPublicShortUrls()
      .stream().map(entityMapper::toShortUrlDto).toList();
  }
}
