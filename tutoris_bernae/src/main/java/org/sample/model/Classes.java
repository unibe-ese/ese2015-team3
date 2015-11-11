package org.sample.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Models the classes that a tutor can register.
 * @author pf15ese
 *
 */
@Entity
public class Classes {
	
	@Id
    @GeneratedValue
    private Long id;
	
	//Currently not in use but could be required for more granular searches.
    @ManyToOne
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
