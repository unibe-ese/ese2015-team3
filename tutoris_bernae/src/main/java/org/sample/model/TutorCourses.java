package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class TutorCourses {

    @Id
    @GeneratedValue
    private Long id;
    
    //    @ManyToOne
    private Long tutor;
    
    //    @ManyToOne
    private Long studycourse;
    
    private boolean isMajor;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMajor() {
		return isMajor;
	}

	public void setMajor(boolean isMajor) {
		this.isMajor = isMajor;
	}
}
