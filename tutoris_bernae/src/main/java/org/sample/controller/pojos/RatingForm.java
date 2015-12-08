package org.sample.controller.pojos;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Stores a message consisting of a subject, a text and the wished receiver
 */
public class RatingForm {

    @NotEmpty
    private String feedback;
    
    private Long id;
    
    private Long ratedTutorId;
    
    @NotNull
    @DecimalMin(value="1")
    @DecimalMax(value="5")
    @Digits(integer=1,fraction=0)
    private BigDecimal rating = new BigDecimal(3);

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public Long getRatedTutorId() {
		return ratedTutorId;
	}

	public void setRatedTutorId(Long ratedTutorId) {
		this.ratedTutorId = ratedTutorId;
	}
    
}
