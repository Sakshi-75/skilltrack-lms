package com.skilltrack.api.web.mapper;

import com.skilltrack.api.web.dto.CourseDetailResponse;
import com.skilltrack.api.web.dto.CourseModuleResponse;
import com.skilltrack.api.web.dto.EnrollmentResponse;
import com.skilltrack.common.domain.course.CourseEntity;
import com.skilltrack.common.domain.course.CourseModuleEntity;
import com.skilltrack.common.domain.enrollment.EnrollmentEntity;
import java.util.List;
import java.util.stream.Collectors;

public final class ApiMapper {

    private ApiMapper() {
    }

    public static CourseDetailResponse toCourseDetailResponse(CourseEntity course) {
        List<CourseModuleResponse> modules = course.getModules().stream()
                .map(ApiMapper::toCourseModuleResponse)
                .collect(Collectors.toList());
        return new CourseDetailResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                modules
        );
    }

    public static CourseModuleResponse toCourseModuleResponse(CourseModuleEntity module) {
        return new CourseModuleResponse(
                module.getId(),
                module.getTitle(),
                module.getContent(),
                module.getSortOrder()
        );
    }

    public static EnrollmentResponse toEnrollmentResponse(EnrollmentEntity enrollment) {
        return new EnrollmentResponse(
                enrollment.getUser().getId(),
                enrollment.getCourse().getId(),
                enrollment.getStatus(),
                enrollment.getCompletedAt()
        );
    }
}
