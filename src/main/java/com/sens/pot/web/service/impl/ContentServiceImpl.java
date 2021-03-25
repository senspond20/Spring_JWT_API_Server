package com.sens.pot.web.service.impl;

import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.repository.content.CategoryRepository;
import com.sens.pot.web.service.ContentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{
    
    private final CategoryRepository categoryRepository;
 
    @Override
    @Transactional
    public Category saveCategory(String name, String description){
        return categoryRepository.save(Category.builder()
                                               .name(name)
                                               .description(description)
                                               .build());
    }
    @Override
    @Transactional
    public Category saveCategory(String code, String name, String description){
        return categoryRepository.save(Category.builder()
                                               .code(code)
                                               .name(name)
                                               .description(description)
                                               .build());
    }

    @Override
    @Transactional
    public Category updateCategory(String code, String name, String description){
        Category category = categoryRepository.findByCode(code);
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public void deleteCategoy(String code){
        categoryRepository.deleteByCode(code);;
    }


}
