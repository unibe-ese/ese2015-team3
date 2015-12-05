package org.sample.controller.pojos;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A register form for users, stores all needed
 * information to create a new user
 */
public class RegisterForm implements FormWithUserDetails {


    private Long id;

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String username;
    
    // password must have 1 uppercase, 1 digit, 1 special character. 8-14 letters
    @Pattern(regexp = "(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,15}$")
    private String password;
    
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String email;

    /* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#getFirstName()
	 */
    @Override
	public String getFirstName() {
        return firstName;
    }

    /* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#getPassword()
	 */
    @Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#getLastName()
	 */
    @Override
	public String getLastName() {
        return lastName;
    }

    /* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#setLastName(java.lang.String)
	 */
    @Override
	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#getEmail()
	 */
    @Override
	public String getEmail() {
        return email;
    }

    /* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#setEmail(java.lang.String)
	 */
    @Override
	public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	/* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#setUsername(java.lang.String)
	 */
	@Override
	public void setUsername(String userName) {
		this.username = userName;
	}

	/* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#getUserId()
	 */
	/** 
	 * RegisterForm stores a new user so his userId is treated as 0
	 * Therefore this always return 0L;
	 */
	public Long getUserId() {
		return 0L;
	}
	
	/* (non-Javadoc)
	 * @see org.sample.controller.pojos.FormWithUserDetails#setUserId(java.lang.Long)
	 */
	/** 
	 * RegisterForm stores a new user so his userId is treated as 0
	 * Therefore you cannot set it (trying so will do absolutely nothing)
	 */
	public void setUserId(Long userId) {
	}

}
