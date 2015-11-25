package org.sample.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * A message send form one user to another
 * @author pf15ese
 */

@Entity
public class TutorShip {
	
	@Id
	@GeneratedValue
	private Long id;

	private Boolean confirmed = false;
	
	@OneToOne
	private User student;
	
	@OneToOne
	private Tutor tutor;
	
	public TutorShip() {

	}

	public TutorShip(Tutor tutor, User student) {
		this.tutor = tutor;
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

}