package com.baeldung.ls.task.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
//todo neeed lombok!
public class TaskDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private String name;
    private String description;
    private LocalDate dueDate;
    private String status;

    public TaskDto(Long id, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, String name, String description, LocalDate dueDate, String status) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) && Objects.equals(createdAt, taskDto.createdAt) && Objects.equals(lastUpdatedAt, taskDto.lastUpdatedAt) && Objects.equals(name, taskDto.name) && Objects.equals(description, taskDto.description) && Objects.equals(dueDate, taskDto.dueDate) && Objects.equals(status, taskDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, lastUpdatedAt, name, description, dueDate, status);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                '}';
    }

    public static TaskDto convertToTaskDto(Task task) {
        return new TaskDto(task.getId(),
                task.getCreatedAt(),
                task.getLastUpdatedAt(),
                task.getName(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus().name());
    }
}
