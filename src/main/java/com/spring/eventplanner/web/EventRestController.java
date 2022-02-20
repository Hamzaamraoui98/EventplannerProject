package com.spring.eventplanner.web;

import com.spring.eventplanner.entities.*;
import com.spring.eventplanner.repositories.DateEventRepository;
import com.spring.eventplanner.repositories.EventRepository;
import com.spring.eventplanner.repositories.TypeEventRepository;
import com.spring.eventplanner.repositories.UserEventRepository;
import com.spring.eventplanner.repositories.UserRepository;
import com.spring.eventplanner.repositories.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class EventRestController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TypeEventRepository typeEventRepository;
    @Autowired
    private UserEventRepository userEventRepository;
    @Autowired
    private DateEventRepository dateEventRepository;
    @Autowired
    private VoteRepository voteRepository;
    
    
    @PostMapping(path = "{username}/events/add",consumes={"application/json"})
    private void addEvent(@PathVariable String username, @RequestBody Event event) {
    	Event newlyAddedEvent = eventRepository.save(event);
        User user=userRepository.findByUsername(username).get(0);
        UserEventId key = new UserEventId(user.getId(), newlyAddedEvent.getId());
        UserEvent toSave = new UserEvent(key, user, newlyAddedEvent, UserEvent.STATUS_CREATOR);
        userEventRepository.save(toSave);
        System.out.println("nombre d objects usereventstatus="+event.getUserEventStatus().size());
        List <UserEvent> userEventStatus=event.getUserEventStatus();
        for (UserEvent oneUserEvent : userEventStatus) {
            User user_invited = oneUserEvent.getUser();
            UserEventId key_invited = new UserEventId(user_invited.getId(), newlyAddedEvent.getId());
            toSave = new UserEvent(key_invited, user_invited, newlyAddedEvent, UserEvent.STATUS_INVITED);
            userEventRepository.save(toSave);
        }
        //ajout des dates
        List<DateEvent> dateEventList=event.getEvent_dates();
        for(DateEvent onedate:dateEventList){
            dateEventRepository.save(new DateEvent(null,onedate.getStart(),onedate.getEnd(),newlyAddedEvent));
        }
    }



    @GetMapping(path="/events")
    public List<Event> getAllEvent( ){
        return eventRepository.findAll();
    }

    @GetMapping(path="events/{id}")
    public Event getEvent( @PathVariable long id ){

            try{ return eventRepository.findById(id).get();}
            catch (Exception e){
                return null;
            }


    }

    @GetMapping(path = "{username}/events/pending")
    public List<Event> getPendingMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_INVITED);
    }
    
    @GetMapping(path = "{username}/events/visit")
    public List<Event> getPendingMeetingsNotVisited(@PathVariable String username) {
    	List<User> users = userRepository.findByUsername(username);
        User relevantUser = users.get(0);
        //System.out.println(relevantUser.getFirstname());
        List<Event> res = new ArrayList<>();
        List<UserEvent> pendingUserMeetings = userEventRepository.findByStatut(UserEvent.STATUS_INVITED);
        pendingUserMeetings = pendingUserMeetings.stream().filter(e->!e.isShowed()).collect(Collectors.toList());
        
        for (UserEvent oneUserEvent : pendingUserMeetings) {
            UserEventId key = oneUserEvent.getId();

            if (key.getUserId() == relevantUser.getId() )
                res.add(eventRepository.findById(key.getEventId()).get());
        }

        return res;
    }

    @GetMapping(path = "{username}/events/accepted")
    public List<Event> getAcceptedMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_ACCEPTED);
    }

    @GetMapping(path = "{username}/events/created")
    public List<Event> getCreatedMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_CREATOR);
    }

    @GetMapping(path = "{username}/events/rejected")
    public List<Event> getRejectedMeetings(@PathVariable String username) {
        return getEvents(username,UserEvent.STATUS_REJECTED);
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
    	Event e= (eventRepository.getById(userEvent.getId().getEventId()));
    	//System.out.println(e.getTitle());
        UserEventId key = userEvent.getId();
        //Event e=eventRepository.getById(userEvent.getId().getEventId());
        UserEvent toUpdate =  null;
        toUpdate= (userEventRepository.findById(key).isPresent()) ? userEventRepository.findById(key).get():userEvent;
        toUpdate.setStatut(userEvent.getStatut());
        toUpdate.setEvent(e);
        UserEvent updatedUserEvent = userEventRepository.save(toUpdate);
        
        return new ResponseEntity<>(updatedUserEvent, HttpStatus.OK);
    }

    //supprimer un evenement
    @DeleteMapping(path="deleteevent/{id}")
    public ResponseEntity<String> deleteevent(@PathVariable Long id){
        System.out.println("je vais supprimer un evenement");
        eventRepository.deleteById(id);
        return new ResponseEntity<String>("event is deleted successfully.!", HttpStatus.OK);
    }
    @GetMapping(path="/gettypeevents")
    public List<TypeEvent> getalltypevents(){
        return typeEventRepository.findAll();
    }
    
    @PutMapping(path="/events/{event_id}/{username}/votedate")
    public ResponseEntity<UserVote> voteforeventdate(@PathVariable Long event_id,@PathVariable String username,@RequestBody DateEvent dateEvent ){
    	UserVote userVote=new UserVote();
    	userVote.setDateevent(dateEvent);
    	userVote.setId(new UserEventId(userRepository.findByUsername(username).get(0).getId(),event_id));
    	userVote=voteRepository.save(userVote);
    	// increment or decrement votes !!!
    	DateEvent ss=dateEventRepository.getById(dateEvent.getId());    	
    	boolean voted=false;
    	
    	for(int i=0;i<ss.getVotes().size();i++) {
    		System.out.println(ss.getVotes().get(i).getId().getUserId());
    		if(ss.getVotes().get(i).getId().getUserId() == userVote.getId().getUserId()) {
    			voted=true;
    			break;
    		}
    	}
    	if(voted==false) {
    		ss.getVotes().add(userVote);
    	}
        ss=dateEventRepository.save(ss);
    	return new ResponseEntity<>(userVote,HttpStatus.OK);
    
    }
    
    @GetMapping(path="/events/{event_id}/{username}/datevoted")
    public ResponseEntity<DateEvent> getDateVoted(@PathVariable String username, @PathVariable Long event_id) {
    	Long user_id=userRepository.findByUsername(username).get(0).getId();
    	Event event=eventRepository.getById(event_id);
    	for(DateEvent ss:event.getEvent_dates()){
    		for(UserVote vote:ss.getVotes())
    			if(vote.getId().getUserId() ==user_id) {
    				System.out.println(ss);
    		    	return new ResponseEntity<>(ss,HttpStatus.OK);
    			}
    	
    }
    	return new ResponseEntity<>(null,HttpStatus.OK);
    }
}







