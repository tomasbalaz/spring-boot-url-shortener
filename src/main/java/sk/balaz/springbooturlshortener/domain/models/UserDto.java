package sk.balaz.springbooturlshortener.domain.models;

import java.io.Serializable;

/**
 * DTO for {@link sk.balaz.springbooturlshortener.domain.entities.User}
 */
public record UserDto(Long id, String name) implements Serializable {
}