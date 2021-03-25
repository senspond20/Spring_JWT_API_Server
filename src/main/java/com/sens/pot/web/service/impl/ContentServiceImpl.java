package com.sens.pot.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        // prefix : 2자리
        final String prefix= "CT";  
        // 오늘날짜 초-밀리세컨드초 : 4자리 
        SimpleDateFormat sf = new SimpleDateFormat("sSSS");
        String sssTime = sf.format(new Date()).toString();
        sf = null;
        // 0~65 난수 : 1자리 문자
        char numChar = (char) Math.round(Math.random() * 66);
        // 2 + 4 + 1 = 7자리 문자열
        String code = (prefix + sssTime + numChar).substring(0, 7);
        return saveCategory(code, name, description);
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
