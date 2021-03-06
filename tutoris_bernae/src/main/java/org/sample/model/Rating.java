package org.sample.model;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

/**
 * A rating given by a user to a tutor.
 * {@code id} automatically generated unique identifier
 * {@code commentator} relationship to user that commits the rating.
 * {@code feedback} can contain several lines of text.
 * 
 * @version 1.0
 * @author ESE Team 3
 */
@Entity
public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User commentator;
	
	private BigDecimal rating;
	
	@Type(type="text")
	private String feedback;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCommentator() {
		return commentator;
	}

	public void setCommentator(User commentator) {
		this.commentator = commentator;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feedback == null) ? 0 : feedback.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
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
		Rating other = (Rating) obj;
		if (feedback == null) {
			if (other.feedback != null)
				return false;
		} else if (!feedback.equals(other.feedback))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}
	
	

}
