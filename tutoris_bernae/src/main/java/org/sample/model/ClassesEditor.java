package org.sample.model;

import java.beans.PropertyEditorSupport;

import org.sample.model.dao.ClassesDao;

/**
 * Offers Services for accessing classDao. Can also convert prototypes to real classes and back.
 * @author	pf15ese
 *
 */

public class ClassesEditor extends PropertyEditorSupport {

    private ClassesDao classesDao;

	public ClassesEditor(ClassesDao classesDao) {
		this.classesDao = classesDao;
	}



	@Override
	public void setAsText(String value)
	{
		setValue(classesDao.findOne(Long.valueOf(value)));
		/*
		Classes classes = classesDao.findOne(prototype.getClassesId());
		return new CompletedClasses(classes,prototype.getGrade());*/
	}
	
	/*@Override
    @Transactional
	public String getAsText()
	{
		setValue(classesDao.findOne(Long.valueOf(value)));
		/*
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
	}*/
}
