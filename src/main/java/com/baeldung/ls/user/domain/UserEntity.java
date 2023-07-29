package com.baeldung.ls.user.domain;

import com.baeldung.ls.global.jpa.BaseEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "users")
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String password;
    private String matchingPassword;

    /*dec for manyTomany option, persist - when save parent user role will be save as well
            for egxamle we will add new role and save it with user, and new user will be added to role.
            merge as well if we will update user role will be updated also (we can add new roles etc etc
            */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String name, String password, String matchingPassword) {
        this.name = name;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(matchingPassword, that.matchingPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, password, matchingPassword);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                '}';
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}
