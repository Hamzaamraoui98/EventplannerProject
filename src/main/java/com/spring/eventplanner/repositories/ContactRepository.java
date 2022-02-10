package com.spring.eventplanner.repositories;

import com.spring.eventplanner.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {

}
