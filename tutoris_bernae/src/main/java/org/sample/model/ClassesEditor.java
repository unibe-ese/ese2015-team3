package org.sample.model;

import java.beans.PropertyEditorSupport;

import org.sample.model.dao.ClassesDao;


public class ClassesEditor extends PropertyEditorSupport {

    private ClassesDao classesDao;


    //Spring needs an empty one for some reason...
	public ClassesEditor() {
	}
	
	public ClassesEditor(ClassesDao classesDao) {
		this.classesDao = classesDao;
	}

	@Override
	public void setAsText(String value)
	{
		setValue(classesDao.findOne(Long.valueOf(value)));
	}
	
}
