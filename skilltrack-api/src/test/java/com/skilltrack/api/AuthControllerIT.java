package com.skilltrack.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.skilltrack.api.web.dto.LoginRequest;
import com.skilltrack.api.web.dto.LoginResponse;
import com.skilltrack.api.web.dto.RegisterRequest;
import com.skilltrack.api.web.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void registerThenLoginSucceeds() {
        RegisterRequest registerRequest = new RegisterRequest(
                "new-user@example.com",
                "password",
                "New User"
        );

        ResponseEntity<UserResponse> registerResponse = restTemplate.postForEntity(
                "/api/auth/register",
                registerRequest,
                UserResponse.class
        );

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(registerResponse.getBody()).isNotNull();
        assertThat(registerResponse.getBody().email()).isEqualTo("new-user@example.com");

        LoginRequest loginRequest = new LoginRequest(
                "new-user@example.com",
                "password"
        );

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "/api/auth/login",
                loginRequest,
                LoginResponse.class
        );

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody().accessToken()).isNotBlank();
    }
}

