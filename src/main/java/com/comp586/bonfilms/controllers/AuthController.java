package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.models.AuthRequest;
import com.comp586.bonfilms.models.AuthResponse;
import com.comp586.bonfilms.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    try {
      return ResponseEntity.ok(authService.login(request));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody AuthRequest request) {
    try {
      authService.register(request);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@RequestBody AuthRequest request) {
    authService.requestPasswordReset(request.email());
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
