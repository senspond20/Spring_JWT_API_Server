package com.sens.pot.web.contents;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.repository.content.CategoryRepository;
import com.sens.pot.web.service.ContentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CategoryTest {
    
    private final String prefix = "CT"; // 카테고리 업무코드

    @Autowired
    private CategoryRepository categoryRepository;

    
    @Autowired
    private ContentService contentService;

    @Test
//    @Transactional
    void test232(){
        categoryRepository.save(Category.builder().name("백엔드").description("백엔드 정리").build());
        System.out.println("=======================================");

        Optional<Category> category = categoryRepository.findById("CT56453");
        System.out.println(category);
        // category.setName("HTML");
        // category.setDescription("HTML에 대한 설명입니다");

        // contentService.updateCategory("CT56453", "HTML", "HTML에 대한 설명입니다");

        // category = categoryRepository.findByCode("CT56453");
        // System.out.println(category);

    }
    @Test
    void test(){
        System.out.println(LocalDateTime.now());

        // 중복되지 않는 고유키 생성

        // 밀리세컨드초를 3자리 
        SimpleDateFormat sf = new SimpleDateFormat("SSS");
    
        // 0~9 랜덤 한자리 추가
        int num = (int) Math.round(Math.random()* 10);
      

        String code = prefix + sf.format(new Date()).toString() + "L" + num;
        System.out.println(code);


    }

    
    private void genarateCategoryCode(){

        // 밀리세컨드초 3자리 
        SimpleDateFormat sf = new SimpleDateFormat("SSS");
        String sssTime = sf.format(new Date()).toString();
        sf = null;

        // 0~9 랜덤 1자리
        int num = (int) Math.round(Math.random()* 10);

        // prefix(2) + 3 + 1 = 6자리
        // this.code = prefix + sssTime + num;
    }
}
