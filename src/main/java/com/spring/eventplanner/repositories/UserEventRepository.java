package com.spring.eventplanner.repositories;

import com.spring.eventplanner.entities.Event;
import com.spring.eventplanner.entities.UserEvent;
import com.spring.eventplanner.entities.UserEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, UserEventId> {
    List<UserEvent> findByStatut(int eventStatus );
    List<UserEvent> findByEvent( Event event );
    
}
