package com.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "comment text must not be empty")
    @Column(columnDefinition = "TEXT")
    private String text;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment_likes", cascade = CascadeType.ALL)
    private List<Like> likes;

}
