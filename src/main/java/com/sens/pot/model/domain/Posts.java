package com.sens.pot.model.domain;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name="posts")
@Getter
// @ToString(exclude = {"reply","content"})
@ToString(exclude = {"reply"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends CuTimeEntity {
    
    /**
     * 식별자 ID (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", columnDefinition = "INT(11) unsigned")
    private Long id;

    /**
     * 제목
     */
    @Column(name="title",length = 155)
    private String title;  
    
    /**
     * 좋아요
     */
    @Column(name="liked", columnDefinition = "INT(11)")
    private int liked = 0;  

    /**
     * 1 : 공개 / 0 : 비공개
     */
    @Column(name="is_activce", columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1") 
    private boolean isActive;

    /**
     * 내용 OneToOne
     */
    /*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "posts")
    @JoinColumn(name="content_id")
    private Content content;*/

    private String content;
    /**
     * 카테고리 ManyToOne
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    private Category category; 

    /**
     * 코멘트 OneToMnay
     */
    @OneToMany(fetch = FetchType.LAZY, 
               cascade = {
                    CascadeType.PERSIST, 
                    CascadeType.REMOVE
              }, mappedBy = "posts")
    private List<Reply> reply = new ArrayList<>();


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
       // Assert.hasText(content, "@@@@@@@ : content must not be empty");
       
        this.title = title;
        this.content = content;
        //this.content = new Content(this, content);
        this.category = category;
        this.liked = 0;
        this.isActive = true;
    }
    // 코멘트 추가
    public void addReply(String reply){
        this.reply.add(new Reply(this, reply));
    }

    // Update
    public void updateTitle(String title){
        this.title = title;
    }
    public void updateIsActive(Boolean isActive){
        this.isActive = isActive;
    }
    public void updateContent(String content){
        // this.content = new Content(this, content);
        this.content = content;
    }
    public void updateCategory(Category category){
        this.category = category;
    }
    public void updateLiked(){
        this.liked++;
    }


}

