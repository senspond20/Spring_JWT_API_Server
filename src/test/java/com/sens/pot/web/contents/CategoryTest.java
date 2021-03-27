package com.sens.pot.web.contents;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.repository.content.CategoryRepository;
import com.sens.pot.web.service.ContentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Assert;

@SpringBootTest

public class CategoryTest {
    
    private final String prefix = "CT"; // 카테고리 업무코드

    @Autowired
    private CategoryRepository categoryRepository;

    
    @Autowired
    private ContentService contentService;

    @Test
    @Transactional   // 트랜잭션
    @Rollback(false) // 테스트 롤백하지 않고 디비에 저장
    void CodeGenerator_Test(){
        Category category = categoryRepository.save(Category.builder().name("프론트 엔드").description("프론트 엔드 정리하는 카테고리 입니다").build());
        System.out.println("=======================================");
        Date date1 = category.getCreateAt();
        Date date2 = categoryRepository.findByName("프론트 엔드").getCreateAt();
        System.out.println(date1);
        System.out.println(date2);
        // Assert.isTrue( date1.equals(date2));
        System.out.println("=======================================");

        
        // Category category = categoryRepository.findByCode("CT56453");
        // System.out.println(category);
        // category.setName("HTML");
        // category.setDescription("HTML에 대한 설명입니다");
        // contentService.updateCategory("CT56453", "HTML", "HTML에 대한 설명입니다");
        // category = categoryRepository.findByCode("CT56453");
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
