package org.sample.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sample.controller.pojos.CompletedClassesPrototype;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.dao.*;

/**
 * Offers Services for accessing classDao. Can also convert prototypes to real classes and back.
 * @author	pf15ese
 *
 */
@Service
public class ClassesService {

	@Autowired
    private ClassesDao classesDao;

    @Transactional
	public CompletedClasses toCompletedClasses(CompletedClassesPrototype prototype)
	{
		Classes classes = classesDao.findOne(prototype.getClassesId());
		return new CompletedClasses(classes,prototype.getGrade());
	}
	
	public Set<CompletedClasses> toCompletedClasses(List<CompletedClassesPrototype> prototypes)
	{
		Set<CompletedClasses> completedClassesSet = new HashSet<CompletedClasses>();
		for(CompletedClassesPrototype p : prototypes){
			completedClassesSet.add(toCompletedClasses(p));
		}
		return completedClassesSet;			
	}

	public CompletedClassesPrototype toPrototype(CompletedClasses classes) {
		return new CompletedClassesPrototype(classes.getClasses().getId(),classes.getGrade());	
	}
	
	public Set<CompletedClassesPrototype> toPrototype(Set<CompletedClasses> classesSet) {
		Set<CompletedClassesPrototype> completedClassesPrototypes = new HashSet<CompletedClassesPrototype>();
		for(CompletedClasses nextClasses : classesSet){
			completedClassesPrototypes.add(toPrototype(nextClasses));
		}
		return completedClassesPrototypes;		
	}
}
