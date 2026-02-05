package com.skilltrack.api.web.dto;

import java.util.UUID;

public record CourseModuleResponse(
        UUID id,
        String title,
        String content,
        int sortOrder
) {
}
