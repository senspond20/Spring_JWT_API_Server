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
import javax.swing.text.AbstractDocument.Content;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    // @Column(name="title",length = 155)
    // private String title;

    // @Column(name="content", columnDefinition = "TEXT NOT NULL")
    // private String content;

    @Column(name="create_at") // 생성 날짜
    @CreationTimestamp
    private Date createAt;

    // @Column(name="update_at")
    // @UpdateTimestamp
    // private Date updateAt;
    
    @ManyToOne
    @JoinColumn(name = "code")
    private Category category;
}
