package com.sanket.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(name = "comment_desc", nullable = false)
    private String commentDescription;

    @ManyToOne(fetch=FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch=FetchType.LAZY)
    private User user;
}
