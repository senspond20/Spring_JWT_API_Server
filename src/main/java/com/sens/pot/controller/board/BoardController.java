package com.sens.pot.controller.board;

import com.sens.pot.common.model.CursorResult;
import com.sens.pot.model.domain.Category;
import com.sens.pot.model.domain.Posts;
import com.sens.pot.model.domain.Reply;
import com.sens.pot.model.mapper.ContentMapper;
import com.sens.pot.service.board.BoardService;
import com.sens.pot.service.board.dto.BoardListResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;
    private final ContentMapper contentMapper;

    /**
     * 게시글 저장
     * @param category
     * @param title
     * @param content
     * @return
     */
    @GetMapping("/save")
    public Posts savePost(String category, String title, String content){
        return boardService.savePost(category,title,content);
    }

    @GetMapping("/comment")
    public Posts addCommentToPost(Long postId, String comment){
        return boardService.addCommentToPost(postId,comment);
    };

    @GetMapping("/list")
    public List<Map<String, Object>> getContent(){
        return contentMapper.select_PostsList();
    }


    /**
     * 게시글 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Object findPostDetailById(@PathVariable Long id){
        return boardService.findPostDetailById(id);
    }

    /**
     * 게시글 모두 조회
     * @return
     */
    @GetMapping("/all")
    public List<BoardListResponseDto> findPostAll(){
        return boardService.findPostAll();
    }
    private static final int DEFAULT_SIZE = 10;

    @GetMapping("/page")
    public CursorResult<Posts> get(Long cursorId, Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        return boardService.get(cursorId, PageRequest.of(0, size));
    }
}
