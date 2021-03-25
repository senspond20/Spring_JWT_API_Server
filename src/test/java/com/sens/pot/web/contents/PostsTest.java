package com.sens.pot.web.contents;

import java.util.List;
import java.util.Optional;

import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.domain.content.Posts;
import com.sens.pot.web.repository.content.CategoryRepository;
import com.sens.pot.web.repository.content.PostsMapper;
import com.sens.pot.web.repository.content.PostsRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostsTest {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    void test(){
        Category category = categoryRepository.findByCode("CT56453");
        Posts post = new Posts();
        post.setCategory(category);

        post = postsRepository.save(post);
        System.out.println(post);

    }

    @Test
    void test2(){

        Optional<Posts> post = postsRepository.findById(1L);
        System.out.println(post);
    }

    @Test
    void queryTest(){
        List<Posts> list = postsMapper.findListPosts();
        System.out.println(list.get(0));
        list.stream().forEach(System.out::println);
    }
}
