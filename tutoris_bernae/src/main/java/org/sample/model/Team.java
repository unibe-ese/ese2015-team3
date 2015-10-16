package org.sample.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String teamName;
    private String date;
    
    public Long getId() {
        return id;
    }
    
    // MW: Deleted the cascade for 1to1
    
    public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String firstName) {
        this.teamName = firstName;
    }
	
	
}
