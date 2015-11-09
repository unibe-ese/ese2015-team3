package org.sample.controller.pojos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
/**
 * An edit form for tutors, stores all changeable
 * values of a user and a tutor profile
 */
public class TutorEditForm {

	private Long id;
    private String firstName;
    private String lastName;
    private String username;
    @NotNull
    @Pattern(regexp = ".*", 
    message = "Must be valid password")
    private String password;
	@NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", 
    message = "Must be valid email address")
    private String email;
	
    private BigDecimal fee;

    private List<StudyCourse> studyCourseList; //Studiengang

    private List<Classes> classList;
    
    private String bio;
	private Long userId;
	private Long tutorId;
	public TutorEditForm() {}

    public TutorEditForm(User user, Tutor tutor) {
    	assert(user!=null);
    	assert(tutor!=null);
    	this.userId = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.password = user.getPassword();
		
    	this.tutorId = tutor.getId();
		this.bio = tutor.getBio();
		this.fee = tutor.getFee();
		this.classList = new LinkedList<Classes>(tutor.getClasses());
		this.studyCourseList = new LinkedList<StudyCourse>(tutor.getCourses());
	}

	public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
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

	public List<Classes> getClassList() {
		return classList;
	}

	public void setClassList(List<Classes> classList) {
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

	public Long getTutorId() {
		return tutorId;
	}

	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}
}
