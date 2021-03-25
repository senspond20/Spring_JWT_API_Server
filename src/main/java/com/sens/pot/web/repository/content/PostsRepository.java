package com.sens.pot.web.repository.content;

import java.util.List;
import java.util.Optional;
import com.sens.pot.web.domain.content.Category;
import com.sens.pot.web.domain.content.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>{
    Optional<Posts> findById(Long id);
    List<Posts> findByCategory(Category category);
}
