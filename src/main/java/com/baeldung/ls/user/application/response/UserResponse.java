package com.baeldung.ls.user.application.response;

import com.baeldung.ls.user.domain.UserEntity;

public class UserResponse {
    private Long userId;
    private String userName;

    public UserResponse(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public static UserResponse toCreateUserResponse(UserEntity userEntity) {
        return new UserResponse(userEntity.getId(), userEntity.getName());
    }
}
