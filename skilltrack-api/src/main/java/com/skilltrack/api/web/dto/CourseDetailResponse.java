package com.skilltrack.api.web.dto;

import java.util.List;
import java.util.UUID;

public record CourseDetailResponse(
        UUID id,
        String title,
        String description,
        List<CourseModuleResponse> modules
) {
}
