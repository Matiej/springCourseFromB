package com.baeldung.ls.user.application;

import com.baeldung.ls.user.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();
    Optional<Role> findRoleByName(String roleName);
}
