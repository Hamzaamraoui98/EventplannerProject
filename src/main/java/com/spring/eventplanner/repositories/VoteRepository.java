package com.spring.eventplanner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.eventplanner.entities.DateEvent;
import com.spring.eventplanner.entities.UserEventId;
import com.spring.eventplanner.entities.UserVote;

public interface VoteRepository extends JpaRepository<UserVote,UserEventId> {
}
