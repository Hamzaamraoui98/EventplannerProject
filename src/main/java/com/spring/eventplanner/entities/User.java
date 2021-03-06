package com.spring.eventplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
   // @Column(nullable = false)
    String firstname;
  //  @Column(nullable = false)
    String lastname;
    @Column(nullable = false,unique = true)
    String username;
    private String role;
  //  @Column(nullable = false)
    String email;
 //   @Column(nullable = false)
    String password;
    String bio;
    @Column(nullable = true, length = 64)
    private String avatar;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<UserEvent> userEventStatus;
    @OneToMany(mappedBy="to")

    private List<Contact> followers;
    @OneToMany(mappedBy="from")
    private List<Contact> following;
}
