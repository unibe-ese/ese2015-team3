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
	}
	
}
