package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.entities.User;
import com.comp586.bonfilms.models.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.comp586.bonfilms.services.AuthService;
import com.comp586.bonfilms.repositories.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AuthService authService;

  @MockBean
  private UserRepository userRepository;

  @Test
  void shouldRegisterUser() throws Exception {
    when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
    when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
        .thenAnswer(invocation -> {
          User user = invocation.getArgument(0);
          user.setId(1L);
          return user;
        });

    AuthRequest request = new AuthRequest("test@example.com", "Password123");

    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldLoginAndReturnTokenAndUser() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
    user.setPasswordHash(hashPassword("Password123"));
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

    AuthRequest request = new AuthRequest("test@example.com", "Password123");

    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andExpect(jsonPath("$.user.email").value("test@example.com"));
  }

  private String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(bytes);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  @Test
  void shouldAcceptForgotPasswordRequest() throws Exception {
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

    AuthRequest request = new AuthRequest("test@example.com", "");

    mockMvc.perform(post("/auth/forgot-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isAccepted());
  }
}
