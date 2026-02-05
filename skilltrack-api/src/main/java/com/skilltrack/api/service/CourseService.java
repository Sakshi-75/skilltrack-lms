package com.skilltrack.api.service;

import com.skilltrack.api.error.NotFoundException;
import com.skilltrack.api.repo.CourseRepository;
import com.skilltrack.common.domain.course.CourseEntity;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public CourseEntity getCourseDetail(UUID courseId) {
        return courseRepository.findWithModulesByIdAndDeletedFalse(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));
    }

    @Transactional
    public void softDeleteCourse(UUID courseId) {
        CourseEntity course = courseRepository.findByIdAndDeletedFalse(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));
        course.setDeleted(true);
    }
}
