package com.spring.eventplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
    @OneToMany(mappedBy = "dateevent" ,cascade = CascadeType.REMOVE)
    List<UserVote> votes=new ArrayList<UserVote>();
	
	public DateEvent(Long id,Date start,Date end,Event event) {
		this.id=id;
		this.start=start;
		this.end=end;
		this.event=event;
	}

}