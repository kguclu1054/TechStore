package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EnCokSatilanlar;


public interface EnCokSatilanlarRepository extends JpaRepository<EnCokSatilanlar, Long> {
	
	Optional<EnCokSatilanlar> findByName(String name);
	
}
