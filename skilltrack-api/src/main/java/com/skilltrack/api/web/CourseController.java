package com.skilltrack.api.web;

import com.skilltrack.api.service.CourseService;
import com.skilltrack.api.web.dto.CourseDetailResponse;
import com.skilltrack.api.web.mapper.ApiMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Course catalog and details")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Returns course details including modules. Returns 404 if not found.")
    public CourseDetailResponse getCourse(@PathVariable("id") UUID id) {
        return ApiMapper.toCourseDetailResponse(courseService.getCourseDetail(id));
    }
}
