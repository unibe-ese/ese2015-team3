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
    
    @OneToMany
    private TutorCourses courses; //Studiengang
    
    @OneToMany
    private TutorClasses classes;

    
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

	public TutorCourses getCourses() {
		return courses;
	}

	public void setCourses(TutorCourses courses) {
		this.courses = courses;
	}

	public TutorClasses getClasses() {
		return classes;
	}

	public void setClasses(TutorClasses classes) {
		this.classes = classes;
	}
}
