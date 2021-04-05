package com.sens.pot.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AccessLevel;

// 추후 게시글 안에 업로드된 파일목록 컬럼 추가
@Entity
@EqualsAndHashCode(of = "id")
@Table(name="content")
@Getter
@ToString(exclude = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id", columnDefinition = "INT(11) unsigned")
    private Long id;

    @Column(name="content", columnDefinition = "TEXT NOT NULL")
    private String content; // 컨텐츠

    @OneToOne
    @JoinColumn(name = "post_id")
    private Posts posts;

    // @OneToOne
    // @PrimaryKeyJoinColumn
    // private Posts posts;

    public Content(Posts posts, String content){
        this.posts = posts;
        this.id = posts.getId();
        this.content = content;
    }
    public void setContent(String content){
        this.content = content;
    }

}
