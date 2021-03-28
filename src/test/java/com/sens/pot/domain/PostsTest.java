package com.sens.pot.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import com.sens.pot.model.domain.Category;
import com.sens.pot.model.domain.Posts;
import com.sens.pot.model.domain.Reply;
import com.sens.pot.model.repository.CategoryRepository;
import com.sens.pot.model.repository.PostsRepository;
import com.sens.pot.model.repository.ReplyRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
// @MapperScan(basePackages = "com.sens.pot.web.repository")
public class PostsTest {
    @Autowired
    private PostsRepository postsRepository;

    // @Autowired
    // private PostsMapper postsMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    // @Autowired
    // private ContentMapper contentMapper;

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

    // @Test
    // void queryTest(){
    //     List<Posts> list = postsMapper.findListPosts();
    //     System.out.println(list.get(0));
    //     list.stream().forEach(System.out::println);
    // }

    @Test
    @Transactional
    @Rollback(false)
    void reply_insertTest(){

        // category save
        Category category = categoryRepository.save(Category.builder().name("프론트 엔드").description("프론트엔드 정리").build());
        System.out.println(category);
       
        // Post
        Posts post = Posts.builder()
                          .title("안녕하세요 프론트")
                          .content("반갑습니다!!@#@")
                          .category(category)
                          .build();
                      
        System.out.println(post);
        post = postsRepository.save(post);

        // Iterable<Reply> it = 
        replyRepository.save(new Reply(post,"프론트엔드 알려주세요"));
        replyRepository.save(new Reply(post,"우왕굳"));


        // List<Posts> list = postsRepository.findAll();

        // list.stream().forEach(item ->{
        //     System.out.println(item.getId());
        //     item.getReply().forEach(System.out::println);
        // });        
    }

    // FetchType.LAZY 가 적용되는지 확인
    @Test
    @Transactional 
    // @Rollback(false)
    void seletPost_ReplyOne_Test(){
        // 1번
        Optional<Posts> post = postsRepository.findById(1L); //Optional null일수도 있는 wrapper 클래스 객체
       
        // post가 있으면 해당 게시글에 담긴 댓글리스트 가져온다. 1번 게시글에 있는 답변들 조회
        if( post.isPresent()){
             post.get().getReply()
                       .stream()
                       .forEach(System.out::println);
        }
    }   


        // post.get().addReply(new Reply("답변을 답니다"));
        
        // postsRepository.deleteById(14L);
  

    /**
     * 자식에서 답변달기 Test
     */
    @Test
    @Transactional
    @Rollback(false)
    void addReplyFromPosts_Test1(){
        Optional<Posts> post = postsRepository.findById(1L);
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
        Optional<Posts> post = postsRepository.findById(1L);
       // Test2
        if(post.isPresent())   
            post.get().addReply("안녕하십니까?");
        else
            System.out.println("해당글 없다");
        
    }


    /**
     * Mybatis Mapper 조회 테스트
     */
    // @Test
    // void test_Mapper(){
    //     System.out.println(contentMapper.select_PostsList());

    // }

    /**
     * 게시글 삭제 -> 게시글에 연결된 댓글리스트도 다 삭제되는지 체크
     * (CascadeType.REMOVE)
     */
    @Test
    // @Transactional
    void delete_Post_Test(){
        // Posts post = postsRepository.findById(30L);
        postsRepository.deleteById(1L);
    }

    /**
     * 댓글 삭제
     */
    @Test
    void delete_reply_Test(){
        replyRepository.deleteById(41L);
    }


     /**
     * 카테고리 삭제 > 카테고리에 딸린 포스트 > 포스트에 딸린 댓글 까지 삭제 되는지 테스트
     */
    @Test
    @Transactional
    @Rollback(false)
    void delete_category_Test(){
        categoryRepository.deleteByCode("CT24658");
    }
}
