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
    
    @ManyToOne
    private Tutor tutor;
    
    @ManyToOne
    private StudyCourse studycourse;
    
    private boolean isMajor;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public StudyCourse getStudycourse() {
		return studycourse;
	}

	public void setStudycourse(StudyCourse studycourse) {
		this.studycourse = studycourse;
	}

	public boolean isMajor() {
		return isMajor;
	}

	public void setMajor(boolean isMajor) {
		this.isMajor = isMajor;
	}
}
