package com.baeldung.ls.task.domain;

import com.baeldung.ls.jpa.BaseEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Task extends BaseEntity {

    private String name;
    private String description;
    private LocalDate dueDate;

    private TaskStatus status;

    public Task() {
    }

    public Task(String name, String description, LocalDate dueDate, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, dueDate, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                '}';
    }
}
