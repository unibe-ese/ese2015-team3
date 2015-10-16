package org.sample.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class TutorClasses {

    @Id
    @GeneratedValue
    private Long id;
 
//    @ManyToOne
    private Long tutor;
    
//    @ManyToOne
    private Long classes; //foreign key
    
    private float grade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
}
