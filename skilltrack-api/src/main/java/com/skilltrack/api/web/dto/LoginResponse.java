package com.skilltrack.api.web.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}

