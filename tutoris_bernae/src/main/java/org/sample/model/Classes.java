package org.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Classes {

	@Id
    @GeneratedValue
    private Long id;

    @ManyToOne
//    @JoinColumn(columnDefinition="integer", nullable = false)
    private StudyCourse studycourse;
    
    @Column(nullable=false)
    private String name;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StudyCourse getStudycourse() {
		return studycourse;
	}

	public void setStudycourse(StudyCourse studycourse) {
		this.studycourse = studycourse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
