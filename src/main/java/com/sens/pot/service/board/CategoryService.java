package com.sens.pot.service.board;

import com.sens.pot.model.domain.Category;
import com.sens.pot.model.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 모두 조회
     * @return
     */
    public List<Category> findCategoryAll(){
        return categoryRepository.findAll();
    }

    /**
     * 카테고리 저장
     * @param categoryName
     * @param description
     * @return
     */
    public Category saveCategory(String categoryName, String description){
        Category category = Category.builder()
                                    .name(categoryName)
                                    .description(description)
                                    .build();
        return categoryRepository.save(category);
    }

//    public Category deleteCategory(String categoryName){
////        categoryRepository.deleteByCode();
//    }


}
