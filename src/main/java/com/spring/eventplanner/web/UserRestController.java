package com.spring.eventplanner.web;

import com.spring.eventplanner.entities.User;
import com.spring.eventplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.cert.Extension;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/users")
    public User addNewUser(@RequestBody User user ){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }



    @GetMapping(path="/users")
    public List<User> getAllUserNames(){
        return userRepository.findAll();

    }
    
    @GetMapping(path="/user/{username}")
    public User getByUsername(@PathVariable String username){
        return userRepository.findByUsername(username).get(0);
    }
}
