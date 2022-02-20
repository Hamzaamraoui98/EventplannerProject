package com.spring.eventplanner.web;

import com.spring.eventplanner.entities.Contact;
import com.spring.eventplanner.entities.User;
import com.spring.eventplanner.entities.UserEvent;
import com.spring.eventplanner.repositories.ContactRepository;
import com.spring.eventplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.cert.Extension;
import java.util.List;
import java.util.Optional;


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
    public User addNewUser(@RequestBody User user ) throws IOException {

        User savedUser =userRepository.save(user);

        return savedUser;
    }
    @PutMapping(path = "user/update", consumes={"application/json"})
    public User modifyUser(@RequestBody User user ) throws IOException {
        Optional<User> us=userRepository.findById(user.getId());
        
        User savedUser =userRepository.save(user);

        return savedUser;
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
    @PutMapping ("{username}/image/upload")
    public User uploadimage(@PathVariable String username,@RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        User user=userRepository.findByUsername(username).get(0);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "user-photos/" + user.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        user.setAvatar(fileName);
        return userRepository.save(user);
    }

    @GetMapping("/{username}/image/get")
    public ResponseEntity<?> getProfileImage(@PathVariable String username) {
        Long userId=userRepository.findByUsername(username).get(0).getId();
        try {
            Path imagePath = FileUploadUtil.fetchProfilePhotoByUserId(userId);
            System.out.println(imagePath);
            if (imagePath != null) {
                System.out.println("Getting image from " + imagePath.toString());

                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));

                return ResponseEntity
                        .ok()
                        .contentLength(imagePath.toFile().length())
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                System.out.println("Profile photo not found for user " + userId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}

