package com.spring.eventplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class DateEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Date start;
	Date end;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id")
	@JsonIgnore
	Event event;

}