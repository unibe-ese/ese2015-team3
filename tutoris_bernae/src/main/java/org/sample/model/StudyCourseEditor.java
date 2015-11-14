package org.sample.model;

import java.beans.PropertyEditorSupport;

import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;

/**
 * Offers Services for accessing classDao. Can also convert prototypes to real classes and back.
 * @author	pf15ese
 *
 */

public class StudyCourseEditor extends PropertyEditorSupport {

    private StudyCourseDao studyCourseDao;

	public StudyCourseEditor(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	@Override
	public void setAsText(String value)
	{
		setValue(studyCourseDao.findOne(Long.valueOf(value)));
	}
	
}
