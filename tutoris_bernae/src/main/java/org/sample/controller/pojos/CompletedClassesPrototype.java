package org.sample.controller.pojos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.dao.*;

/**
 * Models the classes that a tutor can register.
 * @author G.Corsini
 *
 */
@Service
public class CompletedClassesPrototype {

	@Autowired
    private ClassesDao classesDao;
	
    private Long classesId;

    private Integer grade;
    
    public CompletedClassesPrototype(){}

	public CompletedClassesPrototype(Long classesId, Integer grade) {
		this.classesId = classesId;
		this.grade = grade;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
    @Transactional
	/*public CompletedClasses toCompletedClasses()
	{
    	if(classesDao==null)
    		System.out.println("why that?");
		Classes classes = classesDao.findOne(classesId);
		return new CompletedClasses(classes,grade);
	}
	
	public static Set<CompletedClasses> toCompletedClasses(List<CompletedClassesPrototype> prototypes)
	{
		Set<CompletedClasses> completedClassesSet = new HashSet<CompletedClasses>();
		for(CompletedClassesPrototype p : prototypes){
			completedClassesSet.add(p.toCompletedClasses());
		}
		return completedClassesSet;			
	}*/

	public Long getClassesId() {
		return classesId;
	}

	public void setClassesId(Long classesId) {
		this.classesId = classesId;
	}
}
