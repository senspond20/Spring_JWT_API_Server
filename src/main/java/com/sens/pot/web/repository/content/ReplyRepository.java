package com.sens.pot.web.repository.content;

import com.sens.pot.web.domain.content.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{

}