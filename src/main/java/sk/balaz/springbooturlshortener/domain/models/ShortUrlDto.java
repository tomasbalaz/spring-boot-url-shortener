package sk.balaz.springbooturlshortener.domain.models;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link sk.balaz.springbooturlshortener.domain.entities.ShortUrl}
 */
public record ShortUrlDto(Long id, String shortKey, String originalUrl,
  Boolean isPrivate, Instant expiresAt,
  UserDto createdBy, Long clickCount,
  Instant createdAt) implements Serializable {
}