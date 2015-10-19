package org.sample.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable=false)
    private String firstName;
    @Column(nullable=false)
    private String lastName;
    @Column(unique = true)
    private String email; //should be unique!
    @Column(unique = true)
    private String username;
   
    @Column(nullable=false)
	private String password;
    
    private boolean isTutor;
    
    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(columnDefinition="integer", nullable=true)
    private Tutor tutor;
    
    private boolean isTimetableActive = false;
//    private Object timetable;
    
/*    // In order to set default values
    @PrePersist
    public void prePersist(){
    	if(isTimetableActive == null)
    		isTimetableActive = false;
    }*/

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsTutor() {
		return isTutor;
	}

	public void setIsTutor(boolean isTutor) {
		this.isTutor = isTutor;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public boolean getIsTimetableActive() {
		return isTimetableActive;
	}

	public void setTimetableActive(boolean isTimetableActive) {
		this.isTimetableActive = isTimetableActive;
	}
}
