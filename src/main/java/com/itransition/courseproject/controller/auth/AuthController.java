package com.itransition.courseproject.controller.auth;

import com.itransition.courseproject.dto.request.auth.LoginRequest;
import com.itransition.courseproject.dto.request.auth.RegisterRequest;
import com.itransition.courseproject.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }
}
