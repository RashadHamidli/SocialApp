package com.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String token;
    @NotNull
    private LocalDateTime createToke;
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
