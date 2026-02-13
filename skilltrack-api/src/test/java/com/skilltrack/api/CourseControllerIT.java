package com.skilltrack.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.skilltrack.api.repo.CourseRepository;
import com.skilltrack.api.repo.UserRepository;
import com.skilltrack.api.web.dto.LoginRequest;
import com.skilltrack.api.web.dto.LoginResponse;
import com.skilltrack.api.web.dto.CourseDetailResponse;
import com.skilltrack.common.domain.course.CourseEntity;
import com.skilltrack.common.domain.course.CourseModuleEntity;
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
class CourseControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Test
    void getCourseReturnsModules() {
        // create an authenticated user and obtain JWT
        UserEntity user = new UserEntity();
        user.setEmail("course-it-" + UUID.randomUUID() + "@example.com");
        user.setDisplayName("Course IT User");
        user.setRole(Role.STUDENT);
        user.setPasswordHash(passwordEncoder.encode("password"));
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(user.getEmail(), "password");
        LoginResponse loginResponse = restTemplate.postForObject(
                "/api/auth/login",
                loginRequest,
                LoginResponse.class
        );

        String token = loginResponse.accessToken();

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

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(token);
        org.springframework.http.HttpEntity<Void> entity =
                new org.springframework.http.HttpEntity<>(headers);

        ResponseEntity<CourseDetailResponse> response = restTemplate.exchange(
                "/api/courses/" + saved.getId(),
                org.springframework.http.HttpMethod.GET,
                entity,
                CourseDetailResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().modules()).hasSize(1);
    }
}
