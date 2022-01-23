package com.spring.eventplanner.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    /*public static int TYPE_CONCERT = 1;
    public static int TYPE_REUNION = 2;
    public static int TYPE_CINEMA = 3;
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable=false)
    String title;
    String adresse;
    String description;
    Date date_debut;
    Date date_fin;
   /* @OneToMany
    List<DateEvent> dateEventList;*/
    @ManyToOne
    TypeEvent typeEvent;
    String file;
    @OneToMany(mappedBy = "event")
    List<UserEvent> userEventStatus;

}
