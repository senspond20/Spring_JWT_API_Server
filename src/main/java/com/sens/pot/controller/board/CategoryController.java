package com.sens.pot.controller.board;

import com.sens.pot.model.domain.Category;
import com.sens.pot.service.board.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    /**
     * 카테고리 모두 조회
     * @return
     */
    @GetMapping("/all")
    public List<Category> findAll(){
        return categoryService.findCategoryAll();
    }

    /**
     * 카테고리 저장
     * @param categoryName
     * @param description
     * @return
     */
    @GetMapping("/save")
    public Category save(String categoryName, String description){
        return categoryService.saveCategory(categoryName,description);
    }


}
