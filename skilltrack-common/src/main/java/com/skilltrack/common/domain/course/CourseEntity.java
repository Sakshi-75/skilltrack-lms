package com.skilltrack.common.domain.course;

import com.skilltrack.common.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class CourseEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "instructor_id", nullable = false)
    private UUID instructorId;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CourseModuleEntity> modules = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getInstructorId() {
        return instructorId;
    }

    public List<CourseModuleEntity> getModules() {
        return modules;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
    }

    public void setModules(List<CourseModuleEntity> modules) {
        this.modules = modules;
    }
}
