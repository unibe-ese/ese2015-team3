package org.sample.controller.pojos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;

/**
 * A register form for tutors, stores all needed
 * information to upgrade a user to a tutor.
 */
public class TutorForm {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin(value="1")
    @Digits(integer=3,fraction=2)
    private BigDecimal fee;

    
    private List<StudyCourse> studyCourseList = new LinkedList<StudyCourse>(); //Studiengang

  
    private List<CompletedClasses> classList = new LinkedList<CompletedClasses>();
    
    @NotEmpty
    private String bio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public List<StudyCourse> getStudyCourseList() {
		return studyCourseList;
	}

	public void setStudyCourseList(List<StudyCourse> studyCourseList) {
		this.studyCourseList = studyCourseList;
	}

	public List<CompletedClasses> getClassList() {
		return classList;
	}

	public void setClassList(List<CompletedClasses> classList) {
		this.classList = classList;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
