package com.sens.pot.model.repository;
import java.util.List;

import com.sens.pot.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
    Role findByCode(String code);

    Role findByRoleName(String roleName);

    List<Role> findAll();
}

