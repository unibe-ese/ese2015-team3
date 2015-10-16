package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Tutor {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User student; //foreign key to student
    
//    @OneToMany
    private Long courses; //Studiengang
    
//    @OneToMany
    private Long classes;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

}
