package org.sample.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

/**
 * Models the tutor.
 * @author G.Corsini
 *
 */
@Entity
public class Tutor implements Serializable {

	
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User student;
    
    private BigDecimal fee;
    
    private BigDecimal averageGrade;
    
    @Type(type="text")
    private String bio;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<StudyCourse> courses; //Studiengang

	@OneToMany(orphanRemoval=true,fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    private Set<CompletedClasses> completedClasses;

    
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

	public Set<CompletedClasses> getCompletedClasses() {
		return completedClasses;
	}

	public void setCompletedClasses(Set<CompletedClasses> completedClasses) {
		this.completedClasses = completedClasses;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
    public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public BigDecimal getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(BigDecimal averageGrade) {
		this.averageGrade = averageGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averageGrade == null) ? 0 : averageGrade.hashCode());
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Tutor other = (Tutor) obj;
		if (averageGrade == null) {
			if (other.averageGrade != null)
				return false;
		} else if (!averageGrade.equals(other.averageGrade))
			return false;
		if (bio == null) {
			if (other.bio != null)
				return false;
		} else if (!bio.equals(other.bio))
			return false;
		if (fee == null) {
			if (other.fee != null)
				return false;
		} else if (!fee.equals(other.fee))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
