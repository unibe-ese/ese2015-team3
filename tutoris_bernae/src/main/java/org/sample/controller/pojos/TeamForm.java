package org.sample.controller.pojos;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TeamForm {


    private Long id;
    private String date;


   @NotNull
   @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]", 
    message = "Must be valid name")
   private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    

    public String getDateOfCreation() {
		return date;
	}

	public void setDateOfCreation(String dateOfCreation) {
		this.date= dateOfCreation.toString();
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
