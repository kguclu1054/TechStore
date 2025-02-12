package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FavoriteItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, Long> {
    List<FavoriteItem> findByUserId(Long userId);

	boolean existsByProductIdAndUserId(Long productId, Long userId);

	long countByProductIdAndUserId(Long productId, Long userId);

	void deleteByUserIdAndProductId(Long userId, Long productId);
}

