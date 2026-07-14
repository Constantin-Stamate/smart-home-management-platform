package com.smarthome.management.platform.repository;

import com.smarthome.management.platform.model.entity.Role;
import com.smarthome.management.platform.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}