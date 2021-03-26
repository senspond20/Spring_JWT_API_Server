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
import org.springframework.test.annotation.Rollback;
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
    @Rollback(false)
    void testests(){
        Category category = Category.builder().name("백엔드").description("백엔드 정리").build();
        System.out.println(category);
        entityManager.persist(category);

    }

    @Test
    void save(){
      
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
    @Transactional
    @Rollback(false)
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

        // Iterable<Reply> it = 
        replyRepository.save(new Reply(post,"답변"));
        replyRepository.save(new Reply(post,"우왕굳"));
        replyRepository.save(new Reply(post,"답변이당"));

        // List<Posts> list = postsRepository.findAll();

        // list.stream().forEach(item ->{
        //     System.out.println(item.getId());
        //     item.getReply().forEach(System.out::println);
        // });        
    }

    @Test
    @Transactional
    // @Rollback(false)
    void seletPost_ReplyOne_Test(){
        // 30 번 게시글 조회
        Optional<Posts> post = postsRepository.findById(36L); //Optional null일수도 있는 wrapper 클래스 객체
       
        // post가 있으면 해당 게시글에 담긴 댓글리스트 가져온다
        if( post.isPresent()){
             post.get().getReply()
                       .stream()
                       .forEach(System.out::println);
        }
      
        // post.get().addReply(new Reply("답변을 답니다"));
        
        // postsRepository.deleteById(14L);
    }

    /**
     * 자식에서 답변달기 Test
     */
    @Test
    @Transactional
    @Rollback(false)
    void addReplyFromPosts_Test1(){
        Optional<Posts> post = postsRepository.findById(36L);
        // Test1
        if(post.isPresent())
          replyRepository.save(new Reply(post.get(),"답변입니다다다"));
        else
          System.out.println("해당글 없다");
    }

    /**
     * 부모에서 답변달기 Test (CascadeType.PERSIST 필요)
     * CascadeType.PERSIST - 부모 엔티티만 영속화 해도 자식 엔티티까지 함께 영속화
     */
    @Test
    @Transactional
    @Rollback(false)
    void addReplyFromPosts_Test2(){
        Optional<Posts> post = postsRepository.findById(21L);

       // Test2
        if(post.isPresent())   
            post.get().addReply("신우쌤은 20살입니다");
        else
            System.out.println("해당글 없다");
        
    }


    /**
     * Mybatis Mapper 조회 테스트
     */
    @Test
    void test_Mapper(){
        System.out.println(contentMapper.select_PostsList());

    }

    /**
     * 게시글 삭제 -> 게시글에 연결된 댓글리스트도 다 삭제되는지 체크
     */
    @Test
    // @Transactional
    void delete_Post_Test(){
        // Posts post = postsRepository.findById(30L);
        postsRepository.deleteById(36L);
    }

    /**
     * 댓글 삭제
     */
    @Test
    void delete_reply_Test(){
        replyRepository.deleteById(41L);
    }
}
