package com.sens.pot.web.contents;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.swing.text.AbstractDocument.Content;

import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.domain.content.Posts;
import com.sens.pot.web.domain.content.Reply;
import com.sens.pot.web.repository.content.CategoryRepository;
import com.sens.pot.web.repository.content.ContentMapper;
import com.sens.pot.web.repository.content.PostsMapper;
import com.sens.pot.web.repository.content.PostsRepository;
import com.sens.pot.web.repository.content.ReplyRepository;


import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@MapperScan(basePackages = "com.sens.pot.web.repository")
public class PostsTest {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private EntityManager entityManager;
    
    @Test
    @Transactional
    void testests(){
        // for(int i=0 ; i < 100; i++){
        Category category = Category.builder().name("백엔드").description("백엔드 정리").build();
        System.out.println(category);
        // }
         entityManager.persist(category);

    }

    @Test
    @Transactional
    void findone(){
        Category category = categoryRepository.findByCode("CT56453");
        List<Posts> posts = postsRepository.findByCategory(category);
        System.out.println(posts);
    }

    @Test
    void test(){
        Category category = categoryRepository.findByCode("CT56453");
        Posts post = new Posts();
        post.updateCategory(category);

        post = postsRepository.save(post);
        System.out.println(post);

    }

    @Test
    @Transactional 
    void test2(){

        List<Posts> post = postsRepository.findAll();
        System.out.println(post);
    }

    @Test
    void queryTest(){
        List<Posts> list = postsMapper.findListPosts();
        System.out.println(list.get(0));
        list.stream().forEach(System.out::println);
    }

    @Test
    void reply_insertTest(){

        // category save
        Category category = categoryRepository.save(Category.builder().name("백엔드").description("백엔드 정리").build());
        System.out.println(category);
        
        // Post
        Posts post = Posts.builder()
                          .title("안녕하세요")
                          .content("반갑습니다")
                          .category(category)
                          .build();

        System.out.println(post);
        post = postsRepository.save(post);

        replyRepository.save(new Reply(post,"답변"));
        replyRepository.save(new Reply(post,"우왕굳"));
        replyRepository.save(new Reply(post,"답변이당"));

        
        // postsRepository.delete(post);
    }

    @Test
    void test_Mapper(){
        System.out.println(contentMapper.select_PostsList());

    }

    @Test
    void delete_Post_Test(){

        postsRepository.deleteById(3L);
    }
}
