package com.spring.eventplanner;

import com.spring.eventplanner.entities.Event;
import com.spring.eventplanner.entities.TypeEvent;
import com.spring.eventplanner.entities.User;

import com.spring.eventplanner.repositories.EventRepository;
import com.spring.eventplanner.repositories.TypeEventRepository;
import com.spring.eventplanner.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    CommandLineRunner start(UserRepository userRepository, EventRepository eventRepository, TypeEventRepository typeEventRepository){
       return args -> {
/*
          eventRepository.save(new Event(null,"conférence sur l'étude à l'étranger","École d’Ingénieurs SeaTech - Université de Toulon, Av. de l'Université, 83130 La Garde","une conférence organisée par le service des relations internationl",new Date(),new Date(), Event.TYPE_REUNION,null,null));
            eventRepository.save(new Event(null,"concert","concer_name","un concert sympa..",new Date(),new Date(), Event.TYPE_CONCERT,null, null));
            eventRepository.save(new Event(null,"reunion de travail","reunion de travail","une réunion sympa..",new Date(),new Date(), Event.TYPE_REUNION,null,null));

*/
           //initialiser databse par des users
            userRepository.save(new User(null,"hamza","amraoui","hamzaamraoui",null,"amraouihamza50@gmail.com",bCryptPasswordEncoder().encode("hamza123"),"mybio",null));

            userRepository.save(new User(null,"test","test","test",null,"test@gmail.com", bCryptPasswordEncoder().encode("asmae123"), "mybio",null));
            userRepository.save(new User(null,"asmae","majdoub","asmaemajdoub",null,"asmaemajdoub@gmail.com", bCryptPasswordEncoder().encode("asmae123"), "mybio",null));

           userRepository.findAll().forEach(user -> {
                System.out.println(user.getFirstname());
            });
           //initisaliser database par des types d evenement
           typeEventRepository.save(new TypeEvent(null,"Conference",null));
           typeEventRepository.save(new TypeEvent(null,"Meeting",null));
           typeEventRepository.save(new TypeEvent(null,"Party",null));
           typeEventRepository.save(new TypeEvent(null,"Concert",null));
           typeEventRepository.save(new TypeEvent(null,"Weeding",null));
           typeEventRepository.save(new TypeEvent(null,"Other",null));


       };


    }


}
