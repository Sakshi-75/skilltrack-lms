package com.skilltrack.common.domain.enrollment;

import com.skilltrack.common.domain.BaseEntity;
import com.skilltrack.common.domain.course.CourseEntity;
import com.skilltrack.common.domain.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "enrollments")
public class EnrollmentEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnrollmentStatus status;

    @Column(name = "completed_at")
    private Instant completedAt;

    public UserEntity getUser() {
        return user;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
