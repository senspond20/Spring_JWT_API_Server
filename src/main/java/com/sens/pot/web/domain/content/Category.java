package com.sens.pot.web.domain.content;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name ="category")
@EqualsAndHashCode(of = "code")
@Getter
@ToString
@NoArgsConstructor
public class Category {

    @Id
    @Column(name="code", length = 7)
    private String code; 

    @Column(name ="name", length = 55, nullable = false)
    private String name; // 카테고리 이름

    @Column(name ="description", length = 155)
    private String description; // 카테고리 설명

    @Column(name="create_at") // 생성 날짜
    @CreationTimestamp
    private Date createAt;

    @Column(name="update_at")
    @UpdateTimestamp
    private Date updateAt;

    // @OneToMany(mappedBy = "category")
    // private List<Posts> posts;

    @Builder
    public Category(String code, String name, String description){
        if(code == null){
            // prefix : 2자리
            final String prefix= "CT";  

            // 오늘날짜 초-밀리세컨드초 : 4자리 
            SimpleDateFormat sf = new SimpleDateFormat("sSSS");
            String sssTime = sf.format(new Date()).toString();
            sf = null;
            // 0~65 난수 : 1자리 문자
            char numChar = (char) Math.round(Math.random() * 66);
            // 2 + 4 + 1 = 7자리 문자열
            this.code = (prefix + sssTime + numChar).substring(0, 7);
        }else{
            this.code = code;
        }
        this.name = name;
        this.description = description;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }

}
