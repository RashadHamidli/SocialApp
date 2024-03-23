package com.company.repositories;

import com.company.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserUsername(String username);

    List<Like> findByPostPostId(Long postId);

}
