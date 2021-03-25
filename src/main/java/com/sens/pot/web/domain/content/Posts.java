package com.sens.pot.web.domain.content;

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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name="title",length = 155)
    private String title;  // 제목

    @Column(name="content", columnDefinition = "TEXT NOT NULL")
    private String content; // 컨텐츠

    @Column(name="create_at") 
    @CreationTimestamp
    private Date createAt; // 생성 날짜

    @Column(name="update_at") 
    @UpdateTimestamp
    private Date updateAt; // 최종 변경날짜
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code")
    private Category category; // 카테고리

    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post_id")
    // @JoinColumn(name = "post_id")
    // private List<Reply> reply = new ArrayList<>();

    @Column(name="liked", columnDefinition = "INT(11)")
    private int liked = 0; // 좋아요 


    //private Tags tags; // 태그
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
