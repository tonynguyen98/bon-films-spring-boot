package com.comp586.bonfilms.services;

import com.comp586.bonfilms.entities.User;
import com.comp586.bonfilms.models.AuthRequest;
import com.comp586.bonfilms.models.AuthResponse;
import com.comp586.bonfilms.models.UserDto;
import com.comp586.bonfilms.repositories.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public AuthResponse login(AuthRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

    if (!verifyPassword(request.password(), user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid email or password.");
    }

    return new AuthResponse(generateToken(user.getEmail()), toDto(user));
  }

  public void register(AuthRequest request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("Email already registered.");
    }

    User user = new User();
    user.setEmail(request.email());
    user.setPasswordHash(hashPassword(request.password()));
    userRepository.save(user);
  }

  public boolean requestPasswordReset(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    return user.isPresent();
  }

  private String generateToken(String email) {
    return Base64.getUrlEncoder().withoutPadding()
        .encodeToString((UUID.randomUUID() + ":" + email).getBytes(StandardCharsets.UTF_8));
  }

  private boolean verifyPassword(String password, String storedHash) {
    return hashPassword(password).equals(storedHash);
  }

  private String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(bytes);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("Unable to hash password.", e);
    }
  }

  private UserDto toDto(User user) {
    return new UserDto(user.getId(), user.getEmail());
  }
}
