package com.sens.pot.web.repository.test;

import com.sens.pot.web.domain.test.Test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestJpaRepository extends JpaRepository<Test, Integer>{
    
}
