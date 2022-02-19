package com.spring.eventplanner.web;

import com.spring.eventplanner.entities.Contact;
import com.spring.eventplanner.entities.User;
import com.spring.eventplanner.entities.UserEvent;
import com.spring.eventplanner.repositories.ContactRepository;
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
    private ContactRepository contactRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/users", consumes={"application/json"})
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

    @GetMapping(path="{usernamefrom}/{usernameto}/addcontact")
    public Contact addcontact(@PathVariable String usernamefrom,@PathVariable String  usernameto){
        System.out.println("i wanna add contact");
        User from=userRepository.findByUsername(usernamefrom).get(0);
        User to=userRepository.findByUsername(usernameto).get(0);
        Contact contact=new Contact(null,from,to);
        return contactRepository.save(contact);
    }
    
    //supprimer un contact
    @DeleteMapping(path="/{username}/{username2}/deletecontact")
    public ResponseEntity<String> deletecontact(@PathVariable String username,@PathVariable String username2){
        System.out.println("je vais supprimer un contact");
        User from=userRepository.findByUsername(username).get(0);
        User to=userRepository.findByUsername(username2).get(0);
        contactRepository.deleteByFromandTo(from, to);
        return new ResponseEntity<String>("contact is deleted successfully.!", HttpStatus.OK);
    }

    /*
    @GetMapping(path="{username}/getcontact/")
    public List<Contact> getcontacts(@PathVariable String username){

        User from=userRepository.findByUsername(username).get(0);
        return contactRepository.find
    }
    */



}
