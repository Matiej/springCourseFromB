package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.task.domain.TaskDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.UUID.randomUUID;

public class ProjectDto {
    private Long id;
    private String UUID = randomUUID().toString();
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private long version;
    private String name;
    private List<TaskDto> tasks;

    public ProjectDto(Long id, String UUID, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, long version, String name, List<TaskDto> tasks) {
        this.id = id;
        this.UUID = UUID;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.version = version;
        this.name = name;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDto that = (ProjectDto) o;
        return version == that.version && Objects.equals(id, that.id) && Objects.equals(UUID, that.UUID) && Objects.equals(createdAt, that.createdAt) && Objects.equals(lastUpdatedAt, that.lastUpdatedAt) && Objects.equals(name, that.name) && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, UUID, createdAt, lastUpdatedAt, version, name, tasks);
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", UUID='" + UUID + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }

    public static ProjectDto convertToProjectDto(Project project) {
        return new ProjectDto(project.getId(),
                project.getUUID(),
                project.getCreatedAt(),
                project.getLastUpdatedAt(),
                project.getVersion(),
                project.getName(),
                project.getTasks().stream()
                        .map(TaskDto::convertToTaskDto)
                        .toList());
    }

    public static List<ProjectDto> convertToProjectDtoList(List<Project> projectList) {
        return projectList.stream().map(ProjectDto::convertToProjectDto).toList();
    }
}
