package org.sample.controller.pojos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.User;
import org.sample.wrapper.ClassesListWrapper;

public class TutorForm {

	private Long id;
	
	private Long userId;
    
    private BigDecimal fee;

    private Long studyCourse;
    private List<StudyCourse> studyCourseList = new LinkedList<StudyCourse>(); //Studiengang

    private Long selectedClassId;
    private List<Classes> classesList = new LinkedList<Classes>();
    private List<Classes> classList = new LinkedList<Classes>();  
    
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

	public Long getStudyCourse() {
		return studyCourse;
	}

	public void setStudyCourse(Long studyCourse) {
		this.studyCourse = studyCourse;
	}

	public List<Classes> getClassList() {
		return classList;
	}

	public void setClassList(List<Classes> classList) {
		this.classList = classList;
	}

	public List<Classes> getClassesList() {
		return classesList;
	}

	public void setClassesList(List<Classes> classesList) {
		this.classesList = classesList;
	}

	public Long getSelectedClassId() {
		return selectedClassId;
	}

	public void setSelectedClassId(Long selectedClassId) {
		this.selectedClassId = selectedClassId;
	}
}
