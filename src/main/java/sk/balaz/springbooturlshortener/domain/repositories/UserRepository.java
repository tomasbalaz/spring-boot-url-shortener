package sk.balaz.springbooturlshortener.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.balaz.springbooturlshortener.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}