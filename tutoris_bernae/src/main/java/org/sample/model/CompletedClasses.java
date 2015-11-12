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
}
