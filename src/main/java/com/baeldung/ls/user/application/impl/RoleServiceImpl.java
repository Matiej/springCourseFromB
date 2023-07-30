package com.baeldung.ls.user.application.impl;

import com.baeldung.ls.user.application.RoleService;
import com.baeldung.ls.user.database.RoleRepository;
import com.baeldung.ls.user.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        return repository.findRoleByRoleEqualsIgnoreCase(roleName);
    }

    @Override
    public Role addRole(Role role) {
        return repository.save(role);
    }
}
