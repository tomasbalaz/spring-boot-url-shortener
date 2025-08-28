package sk.balaz.springbooturlshortener.domain.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.balaz.springbooturlshortener.domain.entities.User;
import sk.balaz.springbooturlshortener.domain.entities.UserRepository;

import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public SecurityUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));
    return new org.springframework.security.core.userdetails.User(
      user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name()))
    );
  }
}
