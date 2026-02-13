package com.skilltrack.api.service;

import com.skilltrack.api.error.ConflictException;
import com.skilltrack.api.error.NotFoundException;
import com.skilltrack.api.error.ValidationException;
import com.skilltrack.api.repo.CourseRepository;
import com.skilltrack.api.repo.EnrollmentRepository;
import com.skilltrack.api.repo.UserRepository;
import com.skilltrack.common.domain.course.CourseEntity;
import com.skilltrack.common.domain.enrollment.EnrollmentEntity;
import com.skilltrack.common.domain.enrollment.EnrollmentStatus;
import com.skilltrack.common.domain.user.UserEntity;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            UserRepository userRepository,
            CourseRepository courseRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public EnrollmentEntity enroll(UUID userId, UUID courseId) {
        validateIds(userId, courseId);
        UserEntity user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        CourseEntity course = courseRepository.findByIdAndDeletedFalse(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        if (enrollmentRepository.existsByUserIdAndCourseIdAndDeletedFalse(userId, courseId)) {
            throw new ConflictException("User already enrolled in course");
        }

        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public EnrollmentEntity complete(UUID userId, UUID courseId) {
        validateIds(userId, courseId);
        EnrollmentEntity enrollment = enrollmentRepository
                .findByUserIdAndCourseIdAndDeletedFalse(userId, courseId)
                .orElseThrow(() -> new NotFoundException("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollment.setCompletedAt(Instant.now());
        return enrollmentRepository.save(enrollment);
    }

    private void validateIds(UUID userId, UUID courseId) {
        if (userId == null || courseId == null) {
            throw new ValidationException("userId and courseId are required");
        }
    }
}
