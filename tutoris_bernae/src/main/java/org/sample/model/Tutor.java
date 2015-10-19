package org.sample.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Tutor implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
//    @JoinColumn(columnDefinition="integer", nullable=false)
    private User student;
    
    @ManyToMany
    private Set<StudyCourse> courses; //Studiengang
    
    @OneToMany
    private Set<Classes> classes;

    
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

	public Set<StudyCourse> getCourses() {
		return courses;
	}

	public void setCourses(Set<StudyCourse> courses) {
		this.courses = courses;
	}

	public Set<Classes> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}
}
