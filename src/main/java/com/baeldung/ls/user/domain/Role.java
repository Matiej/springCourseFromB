package com.baeldung.ls.user.domain;

import com.baeldung.ls.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Role extends BaseEntity {
    @Column(unique = true)
    private String role;
    @ManyToMany(mappedBy = "roles", targetEntity = UserEntity.class)
    private Set<UserEntity> userEntities = new HashSet<>();

    protected Role() {
    }

    public Role(RoleType roleType) {
        this.role = roleType.getRole();
    }

    public String getRole() {
        return role;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role1 = (Role) o;
        return Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }

    public static Role CREATE_DEFAULT() {
        return new Role(RoleType.USER);
    }
}
