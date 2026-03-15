package io.github.martinezdom.repairshop.services;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.repositories.UserRepository;
import io.github.martinezdom.repairshop.entities.User;
import io.github.martinezdom.repairshop.exceptions.UserNotFoundException;
import io.github.martinezdom.repairshop.dtos.TokenResponseDTO;
import io.github.martinezdom.repairshop.dtos.UserLoginDTO;
import io.github.martinezdom.repairshop.dtos.UserRegisterDTO;

@Service
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User registerUser(UserRegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    public TokenResponseDTO loginUser(UserLoginDTO dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Credenciales inválidas");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new UserNotFoundException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);
        return new TokenResponseDTO(token);
    }
}
