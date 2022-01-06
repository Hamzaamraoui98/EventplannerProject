package com.spring.eventplanner.web;

import com.spring.eventplanner.entities.Event;
import com.spring.eventplanner.entities.User;
import com.spring.eventplanner.entities.UserEvent;
import com.spring.eventplanner.entities.UserEventId;
import com.spring.eventplanner.repositories.EventRepository;
import com.spring.eventplanner.repositories.UserEventRepository;
import com.spring.eventplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@RestController
public class EventRestController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEventRepository userEventRepository;

    @PostMapping(path = "{username}/events/add")
    private void addEvent(@PathVariable String username, @RequestBody Event event) {
        Event newlyAddedEvent = eventRepository.save(event);
        User user=userRepository.findByUsername(username).get(0);
        UserEventId key = new UserEventId(user.getId(), newlyAddedEvent.getId());
        UserEvent toSave = new UserEvent(key, user, newlyAddedEvent, UserEvent.STATUS_CREATOR);
        userEventRepository.save(toSave);

        List<UserEvent> userEventStatus = event.getUserEventStatus();
        for (UserEvent oneUserEvent : userEventStatus) {
            User user_invited = oneUserEvent.getUser();
            UserEventId key_invited = new UserEventId(user_invited.getId(), newlyAddedEvent.getId());
            toSave = new UserEvent(key_invited, user_invited, newlyAddedEvent, UserEvent.STATUS_INVITED);
            userEventRepository.save(toSave);
        }
        }



    @GetMapping(path="/events")
    public List<Event> getAllEvent( ){
        return eventRepository.findAll();
    }

    @GetMapping(path="Events/{id}")
    public Event getEvent( @PathVariable long id ){
        return eventRepository.findById(id).get();
    }

    @GetMapping(path = "{username}/events/pending")
    public List<Event> getPendingMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_INVITED);
    }

    @GetMapping(path = "{username}/events/accepted")
    public List<Event> getAcceptedMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_ACCEPTED);
    }

    @GetMapping(path = "{username}/events/created")
    public List<Event> getCreatedMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_CREATOR);
    }

    private List<Event> getEvents(String aUsername, int aEventStatus) {
        List<User> users = userRepository.findByUsername(aUsername);
        User relevantUser = users.get(0);
        System.out.println(relevantUser.getFirstname());
        List<Event> res = new ArrayList<>();
        List<UserEvent> pendingUserMeetings = userEventRepository.findByStatut(aEventStatus);

        for (UserEvent oneUserEvent : pendingUserMeetings) {
            UserEventId key = oneUserEvent.getId();

            if (key.getUserId() == relevantUser.getId())
                res.add(eventRepository.findById(key.getEventId()).get());
        }

        return res;
    }

    @GetMapping(path = "events/{meetingid}/stats/users/accept")
    public List<String> getUsernamesOfUsersWhoAccepted(
            @PathVariable long meetingid ){
        return this.getUsernamesForGivenMeetingStatus(
                meetingid, UserEvent.STATUS_ACCEPTED );
    }

    @GetMapping(path = "events/{meetingid}/stats/users/decline")
    public List<String> getUsernamesOfUsersWhoDeclined(
            @PathVariable long meetingid ){
        return this.getUsernamesForGivenMeetingStatus(
                meetingid, UserEvent.STATUS_REJECTED );
    }

    @GetMapping(path = "events/{meetingid}/stats/users/pend")
    public List<String> getUsernamesOfUsersWhoPended(
            @PathVariable long meetingid ){
        return this.getUsernamesForGivenMeetingStatus(
                meetingid,UserEvent.STATUS_INVITED);
    }

    List<String> getUsernamesForGivenMeetingStatus( long meetingId, int eventStatus ){
        ArrayList<String> res = new ArrayList<>();
        Event relevantEvent = eventRepository.findById( meetingId ).get();

        List<UserEvent> relevantUserEvents = this.userEventRepository.findByEvent( relevantEvent );

        for( UserEvent userEvent: relevantUserEvents ) {
            if(userEvent.getStatut()==eventStatus ) {
                User relevantUser = userEvent.getUser();
                res.add( relevantUser.getUsername() );
            }
        }

        return res;
    }

    //changer le statut lorsque un utilisateur accept ou reject un evenemnt

    @PutMapping(path = "{username}/events/change-status")
    public ResponseEntity<UserEvent> updateEvent(@PathVariable String username,@RequestBody UserEvent userEvent) {

        UserEventId key = userEvent.getId();
        UserEvent toUpdate = userEventRepository.findById(key).get();
        toUpdate.setStatut(userEvent.getStatut());
        UserEvent updatedUserEvent = userEventRepository.save(toUpdate);

        return new ResponseEntity<>(updatedUserEvent, HttpStatus.OK);
    }
}







