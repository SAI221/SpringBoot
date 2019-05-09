package com.bridgelabz.springbootlogin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.springbootlogin.model.User;
@Repository
public interface UserRepository extends  JpaRepository<User, String>{

	public List<User> findByUserNameAndPassword(String userName,String password);
	

}
