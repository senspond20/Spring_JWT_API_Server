package com.sens.pot.web.api.content;

import java.util.List;

import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.domain.content.Posts;
import com.sens.pot.web.repository.content.CategoryRepository;
import com.sens.pot.web.repository.content.PostsRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ContentController {
    private final PostsRepository postsRepository;
    private final CategoryRepository categoryRepository;
    @GetMapping("/post")
    public Object tetetstes(){
        return postsRepository.findAll();
    }

    
    @GetMapping("/postdd")
    public void tetetstes(@RequestParam Long id){
        postsRepository.deleteById(id);
    }

    @GetMapping("/postformcate")
    public Object testestset(){

        Category category = categoryRepository.findByCode("CT56453");
        List<Posts> list = postsRepository.findByCategory(category);
        return list;
    }
}
