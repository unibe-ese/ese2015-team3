package org.sample.controller.pojos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
/**
 * An edit form for tutors, stores all changeable
 * values of a user and a tutor profile
 */
public class TutorEditForm implements FormWithUserDetails,FormWithClassCourseList{
	@NotEmpty
	private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String username;
    @Pattern(regexp = "(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,15}$")
    private String password;

    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String email;

    @NotNull
    @DecimalMin(value="1")
    @Digits(integer=3,fraction=2)
    private BigDecimal fee;
    
    private List<StudyCourse> studyCourseList = new LinkedList<StudyCourse>(); //Studiengang

    private List<CompletedClasses> classList = new LinkedList<CompletedClasses>();
    
    @NotEmpty
    private String bio;
    @NotNull
	private Long userId;
    @NotNull
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
		this.classList = new LinkedList<CompletedClasses>(tutor.getCompletedClasses());
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

	public Long getTutorId() {
		return tutorId;
	}

	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}

}
