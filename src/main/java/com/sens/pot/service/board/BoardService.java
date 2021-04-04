package com.sens.pot.service.board;

import com.sens.pot.model.domain.Category;
import com.sens.pot.model.domain.Posts;
import com.sens.pot.model.domain.Reply;
import com.sens.pot.model.repository.CategoryRepository;
import com.sens.pot.model.repository.PostsRepository;
import com.sens.pot.model.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final PostsRepository postsRepository;
    private final CategoryRepository categoryRepository;
    private final ReplyRepository replyRepository;

    /**
     * 게시글 저장
     * @param category
     * @param title
     * @param content
     * @return
     */
    @Transactional
    public Posts savePost(String category, String title, String content){
        Category cate = categoryRepository.findByName(category);
        Posts post = Posts.builder().category(cate)
                                    .title(title)
                                    .content(content)
                                    .build();
        return postsRepository.save(post);
    }

    /**
     * 게시글 상세 조회
     * @param id
     * @return
     */
    public Object findPostDetailById(Long id){
        Optional<Posts> post = postsRepository.findById(1L);
        List<Reply> reply = null;
        if( post.isPresent()){
            reply = post.get().getReply();
        }
        Map<String,Object> result = new HashMap<>();
        result.put("post", post);
        result.put("reply", reply);
        return result;
    }




    /**
     * 게시글 모두 조회
     * @return
     */
    @Transactional
    public List<Posts> findPostAll(){
        return postsRepository.findAll();
    }




}
