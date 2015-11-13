package org.sample.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Models the courses that a tutor can register.
 * @author G.Corsini
 *
 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tutor == null) ? 0 : tutor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudyCourse other = (StudyCourse) obj;
		if (faculty == null) {
			if (other.faculty != null)
				return false;
		} else if (!faculty.equals(other.faculty))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tutor == null) {
			if (other.tutor != null)
				return false;
		} else if (!tutor.equals(other.tutor))
			return false;
		return true;
	}
	
	
}
