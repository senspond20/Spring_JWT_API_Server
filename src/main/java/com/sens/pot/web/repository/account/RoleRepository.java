package com.sens.pot.web.repository.account;

import java.util.List;

import com.sens.pot.web.domain.account.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByName(String name);
  
    List<Role> findAll();
}
