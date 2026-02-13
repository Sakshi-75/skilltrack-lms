package com.skilltrack.api.web.dto;

public record LoginRequest(
        String email,
        String password
) {
}

