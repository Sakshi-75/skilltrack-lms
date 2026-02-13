package com.skilltrack.api.web.dto;

public record RegisterRequest(
        String email,
        String password,
        String displayName
) {
}

