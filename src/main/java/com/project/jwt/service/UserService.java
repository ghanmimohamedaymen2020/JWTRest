package com.project.jwt.service;

import com.project.jwt.entity.Role;
import com.project.jwt.entity.User;
import com.project.jwt.repository.RoleRepository;
import com.project.jwt.repository.UserRepository;
import com.project.jwt.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("admin"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userRepository.save(user);
    }
    public List<User> getAllUser(){
    	return userRepository.findAll();
    }
   
    


	
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    
    
	public ResponseEntity<User> getUserById(@PathVariable String userName){
		User user = userRepository.findById(userName)
				.orElseThrow(()->new ResourceNotFoundException("user with this id is not found please enter valid id "));
		return ResponseEntity.ok(user) ;
	} 
  
	
	
	//@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id,@RequestBody User userDetaits){
		User user = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("user with this id is not found please enter valid id "));
		if (userDetaits.getUserFirstName() !=null ){
		user.setUserFirstName(userDetaits.getUserFirstName());}
		if (userDetaits.getUserLastName()!=null ){
		user.setUserLastName(userDetaits.getUserLastName());}
		
		
		
		User updateUser = userRepository.save(user);
		
		return ResponseEntity.ok(updateUser) ;
	}
	
}
