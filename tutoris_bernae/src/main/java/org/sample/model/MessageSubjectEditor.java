package org.sample.model;

import java.beans.PropertyEditorSupport;

import org.sample.model.dao.MessageSubjectDao;

/**
 * Offers Services for accessing classDao. Can also convert prototypes to real classes and back.
 * @author	pf15ese
 *
 */

public class MessageSubjectEditor extends PropertyEditorSupport {

    private MessageSubjectDao messageSubjectDao;

	public MessageSubjectEditor(MessageSubjectDao messageSubjectDao) {
		this.messageSubjectDao = messageSubjectDao;
	}

	@Override
	public void setAsText(String value)
	{
		setValue(messageSubjectDao.findOne(Long.valueOf(value)));
	}
	
}
