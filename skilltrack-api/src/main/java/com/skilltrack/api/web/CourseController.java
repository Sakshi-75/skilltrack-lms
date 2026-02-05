package com.skilltrack.api.web;

import com.skilltrack.api.service.CourseService;
import com.skilltrack.api.web.dto.CourseDetailResponse;
import com.skilltrack.api.web.mapper.ApiMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public CourseDetailResponse getCourse(@PathVariable("id") UUID id) {
        return ApiMapper.toCourseDetailResponse(courseService.getCourseDetail(id));
    }
}
