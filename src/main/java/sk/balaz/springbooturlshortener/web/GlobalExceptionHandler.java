package sk.balaz.springbooturlshortener.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sk.balaz.springbooturlshortener.domain.exception.ShortUrlNotFoundException;

@ControllerAdvice
class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShortUrlNotFoundException.class)
    String handleShortUrlNotFoundException(ShortUrlNotFoundException ex) {
      log.error("Short URL not found: {}", ex.getMessage());
      return "error/404";
    }

    @ExceptionHandler(Exception.class)
    String handleException(Exception ex) {
      log.error("Uncaught exception: {}", ex.getMessage());
      return "error/500";
    }
}
