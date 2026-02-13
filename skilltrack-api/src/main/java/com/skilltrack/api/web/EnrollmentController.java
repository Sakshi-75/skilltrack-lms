package com.skilltrack.api.web;

import com.skilltrack.api.error.ValidationException;
import com.skilltrack.api.service.EnrollmentService;
import com.skilltrack.api.web.dto.EnrollRequest;
import com.skilltrack.api.web.dto.EnrollmentResponse;
import com.skilltrack.api.web.mapper.ApiMapper;
import com.skilltrack.common.domain.enrollment.EnrollmentEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Enrollments", description = "Enroll users in courses and mark completion")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{id}/enroll")
    @Operation(summary = "Enroll user in course", description = "Creates enrollment. Returns 409 if already enrolled. Requires valid userId and courseId.")
    public ResponseEntity<EnrollmentResponse> enroll(
            @PathVariable("id") UUID courseId,
            @RequestBody EnrollRequest request
    ) {
        if (request == null || request.userId() == null) {
            throw new ValidationException("userId is required");
        }
        EnrollmentEntity enrollment = enrollmentService.enroll(request.userId(), courseId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiMapper.toEnrollmentResponse(enrollment));
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Mark course as completed", description = "Sets enrollment status to COMPLETED and sets completedAt.")
    public ResponseEntity<EnrollmentResponse> complete(
            @PathVariable("id") UUID courseId,
            @RequestBody EnrollRequest request
    ) {
        if (request == null || request.userId() == null) {
            throw new ValidationException("userId is required");
        }
        EnrollmentEntity enrollment = enrollmentService.complete(request.userId(), courseId);
        return ResponseEntity.ok(ApiMapper.toEnrollmentResponse(enrollment));
    }
}
