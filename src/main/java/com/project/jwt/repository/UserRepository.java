package com.project.jwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.jwt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	
    public User findByuserName(String userName);

}
