package org.sample.controller.pojos;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;

import org.sample.model.Classes;
import org.sample.model.StudyCourse;

/**
 *  Search Form stores filter criteria.
 *  Available filters are: study course, class and fee.
 */
public class SearchForm {
    
    private long id;
    
    //Standard values to avoid null cluttering in the code
    //0 is not used by the framework as an id, so searching for it will always return nothing
    private StudyCourse studyCourse = null;
    private Classes classes = null;
    
    @Digits(integer=3,fraction=2)
    private BigDecimal fee;

    /*Getter & Setters for class variables*/
    
    public BigDecimal getFee() {
        return fee;
    }
    
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

	public StudyCourse getStudyCourse() {
		return studyCourse;
	}

	public void setStudyCourse(StudyCourse studyCourse) {
		this.studyCourse = studyCourse;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}
        
}
