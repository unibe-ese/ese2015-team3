package org.sample.model;

import java.beans.PropertyEditorSupport;

import org.sample.model.dao.StudyCourseDao;

public class StudyCourseEditor extends PropertyEditorSupport {

    private StudyCourseDao studyCourseDao;

	/**
	 * DO NOT USE
	 * for some reason spring wants an empty constructor
	 * use StudyCourseEditor(StudyCourseDao studyCourseDao) instead
	 */
	public StudyCourseEditor() {
	}
	
	public StudyCourseEditor(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	@Override
	public void setAsText(String value)
	{
		setValue(studyCourseDao.findOne(Long.valueOf(value)));
	}
	
}
