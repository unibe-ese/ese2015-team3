package org.sample.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class StudyCourse implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String faculty; //maybe?
    // Not working; Cannot save tutor form with that (detached entity..)
    @ManyToMany(cascade = {CascadeType.ALL})
    private Set<Tutor> tutor;
   
    /*
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Classes> classes;
*/
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
/*
	public Set<Tutor> getTutors() {
		return tutors;
	}

	public void setTutors(Set<Tutor> tutors) {
		this.tutors = tutors;
	}
/*
	public Set<Classes> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}*/
}
