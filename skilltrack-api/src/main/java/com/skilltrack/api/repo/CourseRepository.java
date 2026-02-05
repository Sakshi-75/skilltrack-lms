package com.skilltrack.api.repo;

import com.skilltrack.common.domain.course.CourseEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    Optional<CourseEntity> findByIdAndDeletedFalse(UUID id);

    @EntityGraph(attributePaths = {"modules"})
    Optional<CourseEntity> findWithModulesByIdAndDeletedFalse(UUID id);
}
