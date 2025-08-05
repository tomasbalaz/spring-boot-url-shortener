package sk.balaz.springbooturlshortener.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
}
