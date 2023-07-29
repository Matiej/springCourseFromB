package com.baeldung.ls.user.application.impl;

import com.baeldung.ls.user.application.RoleService;
import com.baeldung.ls.user.application.UserService;
import com.baeldung.ls.user.application.command.CreateUserCommand;
import com.baeldung.ls.user.database.UserRepository;
import com.baeldung.ls.user.domain.Role;
import com.baeldung.ls.user.domain.RoleType;
import com.baeldung.ls.user.domain.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, RoleService roleService, PasswordEncoder encoder) {
        this.repository = repository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public UserEntity save(CreateUserCommand command) {
        Set<Role> roles = prepareRoles(command.getRoles());
        UserEntity userEntity = command.toUserEntity(roles, encoder);
        return repository.save(userEntity);
    }

    private Set<Role> prepareRoles(List<String> roles) {
        if (roles != null && !roles.isEmpty()) {
            return roles.stream()
                    .map(role -> roleService.findRoleByName(role)
                            .orElseThrow(() -> new IllegalStateException("Role " + role + " does not exist in data base!!"))).collect(Collectors.toSet());
        }
        Role role = roleService.findRoleByName(RoleType.USER.getRole())
                .orElseGet(Role::CREATE_DEFAULT);
        Set<Role> defaultRoleSet = new HashSet<>();
        defaultRoleSet.add(role);
        return defaultRoleSet;
    }

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
