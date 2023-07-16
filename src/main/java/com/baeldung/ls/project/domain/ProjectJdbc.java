package com.baeldung.ls.project.domain;

import java.time.LocalDate;
import java.util.Objects;

public class ProjectJdbc {

    private Long id;

    private String name;

    private LocalDate dateCreated;

    public ProjectJdbc(Long id, String name, LocalDate dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public ProjectJdbc(String name, LocalDate dateCreated) {
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectJdbc that = (ProjectJdbc) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateCreated);
    }

    @Override
    public String toString() {
        return "ProjectJdbc{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
