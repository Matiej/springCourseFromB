package com.baeldung.ls.user.controller;

import com.baeldung.ls.user.domain.Role;
import com.baeldung.ls.user.domain.UserEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RestUserResponse {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private String name;
    private List<String> roles;

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



    public RestUserResponse(Long id, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, String name, List<String> roles) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.name = name;
        this.roles = new ArrayList<>(roles);
    }

    public static RestUserResponse toRestUserResponse(UserEntity userEntity) {
        return new RestUserResponse(
                userEntity.getId(),
                userEntity.getCreatedAt(),
                userEntity.getLastUpdatedAt(),
                userEntity.getName(),
                userEntity.getRoles().stream()
                        .map(Role::getRole)
                        .collect(Collectors.toList())
                );
    }

    public static List<RestUserResponse> toRestUserResponse(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(RestUserResponse::toRestUserResponse).collect(Collectors.toList());
    }
}
