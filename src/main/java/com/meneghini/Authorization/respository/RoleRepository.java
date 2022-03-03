package com.meneghini.Authorization.respository;

import com.meneghini.Authorization.model.ERole;
import com.meneghini.Authorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
