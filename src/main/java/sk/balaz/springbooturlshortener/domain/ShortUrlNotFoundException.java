package sk.balaz.springbooturlshortener.domain;

public class ShortUrlNotFoundException extends RuntimeException {
  public ShortUrlNotFoundException(String message) {
    super(message);
  }
}
