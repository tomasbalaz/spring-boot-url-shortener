package sk.balaz.springbooturlshortener.domain.services;

import org.springframework.stereotype.Service;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;
import sk.balaz.springbooturlshortener.domain.repositories.ShortUrlRepository;

import java.util.List;

@Service
public class ShortUrlService {

  private final ShortUrlRepository shortUrlRepository;

  public ShortUrlService(ShortUrlRepository shortUrlRepository) {
    this.shortUrlRepository = shortUrlRepository;
  }

  public List<ShortUrl> getAllShortUrls() {
    return shortUrlRepository.findPublicShortUrls();
  }
}
