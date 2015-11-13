package org.sample.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.sample.model.dao.ClassesDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Models the classes that a tutor can register.
 * @author G.Corsini
 *
 */
@Entity
public class CompletedClasses {
	//Currently not in use but could be required for more granular searches.
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Classes classes;

	private Integer grade;

	public CompletedClasses(){}

	public CompletedClasses(Classes classes, Integer grade) {
		this.classes=classes;
		this.grade=grade;
	}


	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
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
		CompletedClasses other = (CompletedClasses) obj;
		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
