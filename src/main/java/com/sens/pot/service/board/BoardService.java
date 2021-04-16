package com.sens.pot.service.board;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sens.pot.common.model.CursorResult;
import com.sens.pot.common.utils.ObjectMapperUtils;
import com.sens.pot.model.domain.Category;
import com.sens.pot.model.domain.Posts;
import com.sens.pot.model.domain.Reply;
import com.sens.pot.model.repository.CategoryRepository;
import com.sens.pot.model.repository.PostsRepository;
import com.sens.pot.model.repository.ReplyRepository;
import com.sens.pot.service.board.dto.BoardListResponseDto;

import lombok.RequiredArgsConstructor;
import springfox.documentation.swagger2.mappers.ModelMapper;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final PostsRepository postsRepository;
    private final CategoryRepository categoryRepository;
    private final ReplyRepository replyRepository;

    /**
     * 게시글 저장 (추후 사용자 ID추가)
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
        Optional<Posts> post = postsRepository.findById(id);
        List<Reply> reply = null;
        Map<String,Object> result = new HashMap<>();
        if( post.isPresent()){
            Posts item = post.get();
            result.put("post", item);
            result.put("reply", item.getReply());
        }else{
            result.put("post", null);
            result.put("reply", null);
        }
        return result;
    }

    /**
     * 게시글 코멘트 추가 (추후 사용자 ID추가)
     * @param postId
     * @param comment
     */
    @Transactional
    public Posts addCommentToPost(Long postId,String comment){
        Optional<Posts> post = postsRepository.findById(postId);
        // post가 존재하면
        if(post.isPresent()){
            Posts p = post.get();
            p.addReply(comment);
            return p;
        }else{
            return null;
        }
    }


    /**
     * 게시글 모두 조회
     * @return
     */
    @Transactional
    public List<BoardListResponseDto> findPostAll(){
        List<BoardListResponseDto> list = ObjectMapperUtils.mapAll(postsRepository.findAll(), BoardListResponseDto.class);
        // return postsRepository.findAll();
        return list;
    }
    
    public CursorResult<Posts> get(Long cursorId, Pageable page) {
        final List<Posts> boards = getBoards(cursorId, page);
        final Long lastIdOfList = boards.isEmpty() ?
                null : boards.get(boards.size() - 1).getId();

        return new CursorResult<>(boards, hasNext(lastIdOfList));
    }

    private List<Posts> getBoards(Long id, Pageable page) {
        return id == null ?
                postsRepository.findAllByOrderByIdDesc(page) :
                postsRepository.findByIdLessThanOrderByIdDesc(id, page);
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return postsRepository.existsByIdLessThan(id);
    }


}
