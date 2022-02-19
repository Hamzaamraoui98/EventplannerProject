package com.spring.eventplanner.repositories;

import com.spring.eventplanner.entities.Contact;
import com.spring.eventplanner.entities.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ContactRepository extends JpaRepository<Contact,Long> {
	@Transactional
	@Modifying
	
	void deleteByFromandTo(User from,User to);
}
