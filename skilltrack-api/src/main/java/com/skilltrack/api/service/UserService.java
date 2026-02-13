package com.skilltrack.api.service;

import com.skilltrack.api.error.ConflictException;
import com.skilltrack.api.error.ValidationException;
import com.skilltrack.api.repo.UserRepository;
import com.skilltrack.api.web.dto.RegisterRequest;
import com.skilltrack.common.domain.user.Role;
import com.skilltrack.common.domain.user.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity register(RegisterRequest request) {
        if (request == null
                || request.email() == null
                || request.password() == null
                || request.displayName() == null) {
            throw new ValidationException("email, password and displayName are required");
        }

        userRepository.findByEmailAndDeletedFalse(request.email())
                .ifPresent(u -> {
                    throw new ConflictException("Email is already in use");
                });

        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
        user.setRole(Role.STUDENT);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }
}

