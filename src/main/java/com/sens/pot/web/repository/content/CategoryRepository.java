package com.sens.pot.web.repository.content;

import com.sens.pot.web.domain.content.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{

    Category findByCode(String code);
    void deleteByCode(String code);
}
