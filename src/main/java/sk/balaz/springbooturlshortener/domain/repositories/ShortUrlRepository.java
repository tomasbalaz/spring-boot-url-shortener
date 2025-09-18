package sk.balaz.springbooturlshortener.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

 // List<ShortUrl> findByIsPrivateFalseOrderByCreatedAtDesc();

  //@Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false order by su.createdAt desc")
  @Query("select su from ShortUrl su where su.isPrivate = false")
  @EntityGraph(attributePaths = {"createdBy"})
  Page<ShortUrl> findPublicShortUrls(Pageable pageable);

  boolean existsByShortKey(String shortKey);

  Optional<ShortUrl> findByShortKey(String shortKey);

  Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

  @Modifying
  void deleteByIdInAndCreatedById(List<Long> ids, Long userId);

  @Query("select u from ShortUrl u left join fetch u.createdBy")
  Page<ShortUrl> findAllShortUrls(Pageable pageable);
}
