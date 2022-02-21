package com.spring.eventplanner.repositories;

import com.spring.eventplanner.entities.DateEvent;
import com.spring.eventplanner.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface DateEventRepository extends JpaRepository<DateEvent,Long> {
    @Transactional
   void deleteByEvent(Event event);
}
