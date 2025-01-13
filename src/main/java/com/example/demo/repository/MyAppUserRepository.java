package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MyAppUser;

@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, Long>{

	Optional<MyAppUser> findByUsername(String username);
	
}
