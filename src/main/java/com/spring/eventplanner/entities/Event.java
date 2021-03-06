package com.spring.eventplanner.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;

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
    @Column()
    Boolean isPublic;
    @Column(nullable=false)
    String title;
    String adresse;
    @Column( length = 100000, nullable = false )
    String description;
    Date date_debut;
    Date date_fin;
    @OneToMany(mappedBy = "event" ,cascade = CascadeType.REMOVE)
    List<DateEvent> event_dates;
    @ManyToOne
    TypeEvent typeEvent;
    String file;
    @OneToMany(mappedBy = "event",cascade = CascadeType.REMOVE)
    List<UserEvent> userEventStatus;

}
