package com.baeldung.ls.user.database;

import com.baeldung.ls.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleEqualsIgnoreCase(String role);
}
