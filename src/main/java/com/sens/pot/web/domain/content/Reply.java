package com.sens.pot.web.domain.content;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reply")
@Getter
@Setter
@ToString(exclude = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    // Auto : hibernate로 관리하도록 해야 posts 객체로 insert가 된다.
    // IDENTITY 로 할시 주도권이 객체가 아닌 DB에 있다. 
    // replyRepository.save(new Reply(post,"답변")); 에러가 난다.
    @Column(name = "reply_id")
    private Long id;

    // 전통적인 테이블 설계 원칙은 자식에 외래키를 주지만
    // 객체지향론적으로 표현할때는 자들은 Posts에 외래키를 둔다. (실제로는 자식에 외래키가 생성)

    // @ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Posts posts;

    @Column(name="reply", length = 500) // 500자 제한
    private String reply;

    @Column(name="create_at") // 댓글 작성날짜
    @CreationTimestamp
    private Date create_at;

    // 게시물에 대한 답변 작성    
    public Reply(Posts posts, String reply){
        this.posts = posts;
        this.reply = reply;
    }

    
}
