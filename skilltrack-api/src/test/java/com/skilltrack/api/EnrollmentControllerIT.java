package com.skilltrack.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.skilltrack.api.repo.CourseRepository;
import com.skilltrack.api.repo.UserRepository;
import com.skilltrack.api.web.dto.EnrollRequest;
import com.skilltrack.api.web.dto.EnrollmentResponse;
import com.skilltrack.common.domain.course.CourseEntity;
import com.skilltrack.common.domain.user.Role;
import com.skilltrack.common.domain.user.UserEntity;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnrollmentControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void enrollReturnsCreated() {
        UserEntity user = new UserEntity();
        user.setEmail("user-" + UUID.randomUUID() + "@example.com");
        user.setDisplayName("Test User");
        user.setRole(Role.STUDENT);
        UserEntity savedUser = userRepository.save(user);

        CourseEntity course = new CourseEntity();
        course.setTitle("Java Basics");
        course.setDescription("Intro");
        course.setInstructorId(UUID.randomUUID());
        CourseEntity savedCourse = courseRepository.save(course);

        EnrollRequest request = new EnrollRequest(savedUser.getId());
        ResponseEntity<EnrollmentResponse> response = restTemplate.postForEntity(
                "/api/courses/" + savedCourse.getId() + "/enroll",
                request,
                EnrollmentResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status().name()).isEqualTo("ENROLLED");
    }
}
