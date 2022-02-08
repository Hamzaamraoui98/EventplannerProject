package com.spring.eventplanner.repositories;

import com.spring.eventplanner.entities.DateEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateEventRepository extends JpaRepository<DateEvent,Long> {
}
