package com.sanket.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(name = "post_desc", nullable = false)
    private String postDescription;

    @Column(name = "is_public", nullable = false)
    private boolean isPublicPost = true;

    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy="post")
    private List<Comments> comments;
}
