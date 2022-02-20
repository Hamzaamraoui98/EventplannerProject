package com.spring.eventplanner;

import com.spring.eventplanner.entities.*;

import com.spring.eventplanner.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;


@SpringBootApplication
public class EventPlannerApplication {

    public static void main(String[] args) {

        SpringApplication.run(EventPlannerApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    CommandLineRunner start(UserRepository userRepository, EventRepository eventRepository, TypeEventRepository typeEventRepository ,UserEventRepository userEventRepository,ContactRepository contactRepository){
       return args -> {
/*
          eventRepository.save(new Event(null,"conférence sur l'étude à l'étranger","École d’Ingénieurs SeaTech - Université de Toulon, Av. de l'Université, 83130 La Garde","une conférence organisée par le service des relations internationl",new Date(),new Date(), Event.TYPE_REUNION,null,null));
            eventRepository.save(new Event(null,"concert","concer_name","un concert sympa..",new Date(),new Date(), Event.TYPE_CONCERT,null, null));
            eventRepository.save(new Event(null,"reunion de travail","reunion de travail","une réunion sympa..",new Date(),new Date(), Event.TYPE_REUNION,null,null));

*/
           //initialiser databse par des users
//    	   User hamza=new User(null,"hamza","amraoui","hamzaamraoui",null,"amraouihamza50@gmail.com",bCryptPasswordEncoder().encode("hamza123"),"mybio",null,null,null,null);
//	   	   User asmae=new User(null,"asmae","majdoub","asmaemajdoub",null,"asmaemajdoub@gmail.com",bCryptPasswordEncoder().encode("asmae123"),"mybio",null,null,null,null);
//	   	   User alberto=new User(null,"alberto","rodriguez","albertorod",null,"alberto@gmail.com",bCryptPasswordEncoder().encode("alberto123"),"mybio",null,null,null,null);
//	   	    
//	   	   User hamzaaded=userRepository.save(hamza);
//	       User asmaeaded=userRepository.save(asmae);
//	       User albertoaded=userRepository.save(alberto);
//	       contactRepository.save(new Contact(null,hamzaaded,asmaeaded));
//	       //initisaliser database par des types d evenement
//           typeEventRepository.save(new TypeEvent(null,"Conference",null));
//           typeEventRepository.save(new TypeEvent(null,"Meeting",null));
//           typeEventRepository.save(new TypeEvent(null,"Party",null));
//           typeEventRepository.save(new TypeEvent(null,"Concert",null));
//           typeEventRepository.save(new TypeEvent(null,"Weeding",null));
//           typeEventRepository.save(new TypeEvent(null,"Other",null));
//           
//           typeEventRepository.findAll().forEach(type -> {
//               System.out.println(type.getName());
//           });
//           
//	       
//	       //initialize database with events
//	       Event event1=new Event(null,true,"conference sur l'etude a  l'etranger","83160 La Valette-du-Var","une conference organisee par le service des relations internationl",new Date(),new Date(),null,typeEventRepository.findAll().get(0),null,null);
//	       event1=eventRepository.save(event1);
//	 	   Event event2=new Event(null,true,"Silent Wristwatch Film Festival","69100 Villeurbanne","rain assault gang jeans monofilament cyber- 3D-printed marketing. cartel Legba rebar saturation point garage numinous boy gang. gang apophenia physical market nodality digital weathered vinyl. render-farm boat office kanji garage -space car shoes. footage stimulate futurity franchise realism sign sensory office.",new Date(),new Date(),null, typeEventRepository.findAll().get(1),null,null);
//	       eventRepository.save(event2);
//	       Event event3=new Event(null,false,"Augmented Reality Dolphin Drone Demo","7 Rue Linois, 75015 Paris","convenience store advert DIY A.I. franchise warehouse neural uplink. singularity sensory denim Tokyo vinyl skyscraper meta- skyscraper. marketing disposable tower knife plastic artisanal euro-pop concrete. hotdog cardboard dolphin network assassin dolphin receding dome. apophenia woman disposable Kowloon realism long-chain hydrocarbons boat tank-traps.",new Date(),new Date(),null, typeEventRepository.findAll().get(3),null,null);
//	       eventRepository.save(event3);
//	       
//	       userEventRepository.save(new UserEvent(new UserEventId(hamzaaded.getId(), event1.getId()),hamza,event1,1));
//	       userEventRepository.save(new UserEvent(new UserEventId(asmaeaded.getId(), event1.getId()),asmae,event1,2));
//	         
//	       userEventRepository.save(new UserEvent(new UserEventId(asmaeaded.getId(), event2.getId()),asmae,event2,1));
//	       userEventRepository.save(new UserEvent(new UserEventId(hamzaaded.getId(), event2.getId()),hamza,event2,2));
//	       userEventRepository.save(new UserEvent(new UserEventId(albertoaded.getId(), event2.getId()),alberto,event2,3));
//	       
//	       userEventRepository.save(new UserEvent(new UserEventId(albertoaded.getId(), event3.getId()),alberto,event3,1));
//	       userEventRepository.save(new UserEvent(new UserEventId(asmaeaded.getId(), event3.getId()),asmae,event3,4));
//	       
//           userRepository.findAll().forEach(user -> {
//                System.out.println(user.getFirstname());
//            });
//           User user=userRepository.findByUsername("asmaemajdoub").get(0);
//           System.out.println(user.getUsername());
//           
          
       };


    }


}
