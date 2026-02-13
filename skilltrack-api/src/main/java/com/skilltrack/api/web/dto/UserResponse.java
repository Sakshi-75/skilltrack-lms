package com.skilltrack.api.web.dto;

import com.skilltrack.common.domain.user.Role;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String displayName,
        Role role
) {
}

