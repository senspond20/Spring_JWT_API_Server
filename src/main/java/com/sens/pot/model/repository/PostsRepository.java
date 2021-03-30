package com.sens.pot.model.repository;

import java.util.List;
import java.util.Optional;
import com.sens.pot.model.domain.Category;
import com.sens.pot.model.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer>{
    Optional<Posts> findById(Long id);
    List<Posts> findByCategory(Category category);
}
