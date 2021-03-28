package com.sens.pot.model.repository;

import com.sens.pot.model.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{

    Category findByCode(String code);

    Category findByName(String name);
    void deleteByCode(String code);
}
