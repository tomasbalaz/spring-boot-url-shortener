package sk.balaz.springbooturlshortener.domain.services;

import org.springframework.stereotype.Component;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;
import sk.balaz.springbooturlshortener.domain.entities.User;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.models.UserDto;

@Component
public class EntityMapper {

  public ShortUrlDto toShortUrlDto(ShortUrl shortUrl) {
    UserDto userDto = null;
    if(shortUrl.getCreatedBy() != null) {
      userDto = toUserDto(shortUrl.getCreatedBy());
    }

    return new ShortUrlDto(
      shortUrl.getId(),
      shortUrl.getShortKey(),
      shortUrl.getOriginalUrl(),
      shortUrl.getIsPrivate(),
      shortUrl.getExpiresAt(),
      userDto,
      shortUrl.getClickCount(),
      shortUrl.getCreatedAt()
    );
  }

  public UserDto toUserDto(User user) {
    return new UserDto(user.getId(), user.getName());
  }
}
