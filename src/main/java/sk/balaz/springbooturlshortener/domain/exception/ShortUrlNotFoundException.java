package sk.balaz.springbooturlshortener.domain.exception;

public class ShortUrlNotFoundException extends RuntimeException {
  public ShortUrlNotFoundException(String message) {
    super(message);
  }
}
