package org.sample.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

/**
 * A message send form one user to another
 * @author pf15ese
 */

@Entity
public class MessageSubject {

	@Id
	@GeneratedValue
	private Long id;

	private String messageSubjectName;

	private String action;
	
	private String actionBaseLink;
	
    private String genericEmailMessage;
    
    private String role = "ROLE_USER";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageSubjectName() {
		return messageSubjectName;
	}

	public void setMessageSubjectName(String messageSubjectName) {
		this.messageSubjectName = messageSubjectName;
	}

	public String getGenericEmailMessage() {
		return genericEmailMessage;
	}

	public void setGenericEmailMessage(String genericEmailMessage) {
		this.genericEmailMessage = genericEmailMessage;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionBaseLink() {
		return actionBaseLink;
	}

	public void setActionBaseLink(String actionBaseLink) {
		this.actionBaseLink = actionBaseLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((actionBaseLink == null) ? 0 : actionBaseLink.hashCode());
		result = prime * result + ((genericEmailMessage == null) ? 0 : genericEmailMessage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messageSubjectName == null) ? 0 : messageSubjectName.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageSubject other = (MessageSubject) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (actionBaseLink == null) {
			if (other.actionBaseLink != null)
				return false;
		} else if (!actionBaseLink.equals(other.actionBaseLink))
			return false;
		if (genericEmailMessage == null) {
			if (other.genericEmailMessage != null)
				return false;
		} else if (!genericEmailMessage.equals(other.genericEmailMessage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (messageSubjectName == null) {
			if (other.messageSubjectName != null)
				return false;
		} else if (!messageSubjectName.equals(other.messageSubjectName))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	
}
