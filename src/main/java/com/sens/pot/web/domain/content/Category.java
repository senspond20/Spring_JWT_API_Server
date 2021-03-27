package com.sens.pot.web.domain.content;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name ="category")
@EqualsAndHashCode(of = "code")
@Getter
@ToString(exclude = "posts")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Category {

    @Id
    @Column(name="code", length = 7)
    @GeneratedValue(generator = "CodeGenerator")  
    @GenericGenerator(name = "CodeGenerator", strategy = "com.sens.pot.common.helper.jpa.CodeGenerator")  
    private String code; 

    @Column(name ="name", length = 55, nullable = false, unique = true)
    private String name; // 카테고리 이름

    @Column(name ="description", length = 155)
    private String description; // 카테고리 설명

    @Column(name="create_at") //, columnDefinition = "TIMESTAMP NOT NULL default CURRENT_TIMESTAMP") // 생성 날짜
    @CreationTimestamp
    // @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(name="update_at") //, columnDefinition = "TIMESTAMP NULL default NULL ON UPDATE CURRENT_TIMESTAMP") // 초기null 업데이트 시 current_timestamp
    // @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateAt;

    // @OneToMany(mappedBy = "category")
    // private List<Posts> posts;
    @OneToMany(fetch = FetchType.LAZY, 
    cascade = {
         CascadeType.PERSIST, 
         CascadeType.REMOVE
   }, mappedBy = "category")
    @JsonIgnore
    private List<Posts> Posts = new ArrayList<>();

    @Builder
    public Category(String code, String name, String description){
        Assert.hasText(name,"@@@@@@@ : 카테고리 이름은 반드시 필요합니다");
        this.code = code;
        this.name = name;
        this.description = description;
    }

    // 업데이트
    public void updatetName(String name){
        this.name = name;
    }
    public void updateDescription(String description){
        this.description = description;
    }

}
