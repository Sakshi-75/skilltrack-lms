package com.skilltrack.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.skilltrack.api.repo.CourseRepository;
import com.skilltrack.api.web.dto.CourseDetailResponse;
import com.skilltrack.common.domain.course.CourseEntity;
import com.skilltrack.common.domain.course.CourseModuleEntity;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void getCourseReturnsModules() {
        CourseEntity course = new CourseEntity();
        course.setTitle("Intro to Spring");
        course.setDescription("Basics");
        course.setInstructorId(UUID.randomUUID());

        CourseModuleEntity module = new CourseModuleEntity();
        module.setTitle("Module 1");
        module.setContent("Welcome");
        module.setSortOrder(1);
        module.setCourse(course);
        course.getModules().add(module);

        CourseEntity saved = courseRepository.save(course);

        ResponseEntity<CourseDetailResponse> response = restTemplate.getForEntity(
                "/api/courses/" + saved.getId(),
                CourseDetailResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().modules()).hasSize(1);
    }
}
