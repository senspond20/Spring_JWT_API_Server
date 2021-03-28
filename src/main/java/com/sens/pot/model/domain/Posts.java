package com.sens.pot.model.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="posts")
@Getter
@EqualsAndHashCode(of= "id")
@ToString(exclude = "reply")
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    /**
     * 식별자 ID (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "INT(11)")
    private Long id;

    /**
     * 제목
     */
    @Column(name="title",length = 155)
    private String title;  // 제목

    /**
     * 내용 (메모리를 많이 차지하는 컬럼 - 추후 따로 테이블 뺀다. 1:1 관계로 )
     */
    @Column(name="content", columnDefinition = "TEXT NOT NULL")
    private String content; // 컨텐츠

    /**
     * 생성날짜
     */
    @Column(name="create_at") 
    @CreationTimestamp
    private Date createAt; // 생성 날짜

    /**
     * 
     */
    @Column(name="update_at") 
    @UpdateTimestamp
    private Date updateAt; // 최종 변경날짜
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code")
    private Category category; // 카테고리

    @OneToMany(fetch = FetchType.LAZY, 
               cascade = {
                    CascadeType.PERSIST, 
                    CascadeType.REMOVE
              }, mappedBy = "posts")
    // @JoinColumn(name = "post_id")
    private List<Reply> reply = new ArrayList<>();

    @Column(name="liked", columnDefinition = "INT(11)")
    private int liked = 0; // 좋아요 

    @Column(name="is_activce", columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1") // 1 : 공개 / 0 : 비공개
    private boolean isActive;

    // @OneToMany(fetch = FetchType.LAZY, 
    //         cascade = {
    //             CascadeType.PERSIST, 
    //             CascadeType.REMOVE
    //     }, mappedBy = "posts")
    // private Tags tags; // 태그
    
    // 카테고리 지정안할수 있다.
    @Builder
    public Posts(String title, String content, Category category){
        Assert.hasText(title,   "@@@@@@@ : title   must not be empty");
        Assert.hasText(content, "@@@@@@@ : content must not be empty");
       
        this.title = title;
        this.content = content;
        this.category = category;
        this.liked = 0;
    }
    // 코멘트 추가
    public void addReply(String reply){
        this.reply.add(new Reply(this, reply));
    }

    // Update
    public void updateTitle(String title){
        this.title = title;
    }
    public void updateContent(String content){
        this.content = content;
    }
    public void updateCategory(Category category){
        this.category = category;
    }
    public void updateLiked(){
        this.liked++;
    }


}

