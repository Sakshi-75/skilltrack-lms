package com.skilltrack.api.web;

import com.skilltrack.api.error.ValidationException;
import com.skilltrack.api.service.EnrollmentService;
import com.skilltrack.api.web.dto.EnrollRequest;
import com.skilltrack.api.web.dto.EnrollmentResponse;
import com.skilltrack.api.web.mapper.ApiMapper;
import com.skilltrack.common.domain.enrollment.EnrollmentEntity;
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
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{id}/enroll")
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
