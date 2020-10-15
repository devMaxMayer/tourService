package com.company.tourService.repos;

import com.company.tourService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName (String name);
}
