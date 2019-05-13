package com.bridgelabz.springbootform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.springbootform.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, String> {
	public List<UserDetails> findByEmailAndPassword(String email, String password);

	public void deleteByUserName(String userName);
}
