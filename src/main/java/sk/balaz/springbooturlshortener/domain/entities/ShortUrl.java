package sk.balaz.springbooturlshortener.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
public class ShortUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String shortKey;

  @Column(unique = true, nullable = false)
  private String originalUrl;

  @ManyToOne()
  @JoinColumn(name = "created_by")
  private User createdby;

  @Column(nullable = false)
  private LocalDateTime createdAt;
}
