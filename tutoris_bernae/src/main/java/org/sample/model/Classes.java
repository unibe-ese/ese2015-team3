package org.sample.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.sample.model.dao.ClassesDao;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
public class Classes {
	
	@Id
    @GeneratedValue
    private Long id;
	
	//Currently not in use (do we even need to know that?)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(cascade = {CascadeType.ALL})
    private StudyCourse studyCourse;
    
	
    private String name;
    private Integer grade;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StudyCourse getStudyCourse() {
		return studyCourse;
	}

	public void setStudyCourse(StudyCourse studycourse) {
		this.studyCourse = studycourse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}
