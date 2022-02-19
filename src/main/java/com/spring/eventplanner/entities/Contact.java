package com.spring.eventplanner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NamedQuery(name="Contact.deleteByFromandTo" , query="delete from Contact y where y.from = ?1 and y.to = ?2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="from_user_fk")
    @JsonIgnoreProperties({"followers", "following"})
    private User from;
    @ManyToOne
    @JoinColumn(name="to_user_fk")
    @JsonIgnoreProperties({"followers", "following"})
    private User to;
}
