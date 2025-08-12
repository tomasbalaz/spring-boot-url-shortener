package sk.balaz.springbooturlshortener.domain.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

  List<ShortUrl> findByIsPrivateFalseOrderByCreatedAtDesc();

  //@Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false order by su.createdAt desc")
  @Query("select su from ShortUrl su where su.isPrivate = false order by su.createdAt desc")
  @EntityGraph(attributePaths = {"createdBy"})
  List<ShortUrl> findPublicShortUrls();
}
