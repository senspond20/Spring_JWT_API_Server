package com.sens.pot.web.repository.content;

import java.util.List;

import com.sens.pot.web.domain.content.Posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>{

}
