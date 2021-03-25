package com.sens.pot.web.repository.content;

import java.util.List;

import com.sens.pot.web.domain.content.Posts;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostsMapper {

    @Select("SELECT * FROM posts")
    List<Posts> findListPosts();
}
