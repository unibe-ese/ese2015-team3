package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Classes {

    @Id
    @GeneratedValue
    private Long id;

//    @ManyToOne
    private Long studycourse; //foreign key
    
//    @OneToMany
    private Long tutorclasses;
    
    private String name;

    
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
}
