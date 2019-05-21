package com.bridgelabz.fundoonoteapp.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonoteapp.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
	public List<UserDetails> findByEmailAndPassword(String email, String password);

	public void deleteByUserId(int id);

	public Optional<UserDetails> findByUserId(int id);
	
	public List<UserDetails> findByEmail(String email);

	

	
}