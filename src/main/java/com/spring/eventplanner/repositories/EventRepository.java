package com.spring.eventplanner.repositories;

import com.spring.eventplanner.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {

}
