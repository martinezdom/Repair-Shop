package io.github.martinezdom.repairshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.UserRegisterDTO;
import io.github.martinezdom.repairshop.dtos.UserResponseDTO;
import io.github.martinezdom.repairshop.dtos.TokenResponseDTO;
import io.github.martinezdom.repairshop.dtos.UserLoginDTO;
import io.github.martinezdom.repairshop.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserResponseDTO userResponseDTO = userService.registerUser(dto);
        return ResponseEntity.status(201).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO dto) {
        TokenResponseDTO token = userService.loginUser(dto);
        return ResponseEntity.ok(token);
    }
}
