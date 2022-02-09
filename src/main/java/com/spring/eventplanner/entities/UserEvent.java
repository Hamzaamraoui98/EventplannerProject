package com.spring.eventplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.util.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEvent {
    public static int STATUS_CREATOR = 1;
    public static int STATUS_INVITED = 2;
    public static int STATUS_ACCEPTED = 3;
    public static int STATUS_REJECTED = 4;

    @EmbeddedId
    UserEventId id;
    @ManyToOne()
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne()
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    @JsonIgnore
    Event event;
    int statut;
}
