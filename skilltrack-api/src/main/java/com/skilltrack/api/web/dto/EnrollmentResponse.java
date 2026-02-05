package com.skilltrack.api.web.dto;

import com.skilltrack.common.domain.enrollment.EnrollmentStatus;
import java.time.Instant;
import java.util.UUID;

public record EnrollmentResponse(
        UUID userId,
        UUID courseId,
        EnrollmentStatus status,
        Instant completedAt
) {
}
