package com.project.jwt.controller;

import com.project.jwt.entity.User;
import com.project.jwt.repository.UserRepository;
import com.project.jwt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.project.jwt.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@RestController
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})

public class UserController {
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }
   
    
    @GetMapping("/users")

    public  List<User>  getUsres(){
	return userRepository.findAll();        	
    	
    }
    
    
    //@PreAuthorize("hasRole('Admin')")
    @ResponseBody
	@RequestMapping(value = "/users/{id}" , method = RequestMethod.DELETE)
	public void  deleteUser(@PathVariable String id){
		
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user with this id is not found : "+ id));
		
		userRepository.delete(user);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE); 
		
		
	}
    
    
	@GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
		User user = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("user with this id is not found please enter valid id "));
		return ResponseEntity.ok(user) ;
	}
	
	
  
	@PutMapping("/users/{id}")
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
