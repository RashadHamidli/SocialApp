package com.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "post text must not be empty")
    @Column(columnDefinition = "TEXT")
    private String text;
    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;
}