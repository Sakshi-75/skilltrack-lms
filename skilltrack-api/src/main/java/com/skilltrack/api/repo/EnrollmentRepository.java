package com.skilltrack.api.repo;

import com.skilltrack.common.domain.enrollment.EnrollmentEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {

    @Query("""
            select case when count(e) > 0 then true else false end
            from EnrollmentEntity e
            where e.user.id = :userId
              and e.course.id = :courseId
              and e.deleted = false
            """)
    boolean existsByUserIdAndCourseIdAndDeletedFalse(
            @Param("userId") UUID userId,
            @Param("courseId") UUID courseId
    );

    @Query("""
            select e
            from EnrollmentEntity e
            where e.user.id = :userId
              and e.course.id = :courseId
              and e.deleted = false
            """)
    Optional<EnrollmentEntity> findByUserIdAndCourseIdAndDeletedFalse(
            @Param("userId") UUID userId,
            @Param("courseId") UUID courseId
    );
}
