package org.sample.controller.pojos;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;

/**
 *  Search Form stores filter criteria.
 *  Available filters are: study course, class and fee.
 */
public class SearchForm {
    
    private long id;
    
    //Standard values to avoid null cluttering in the code
    //0 is not used by the framework as an id, so searching for it will always return nothing
    private Long studyCourseId = 0L;
    private Long classesId = 0L;
    
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

	public Long getStudyCourseId() {
		return studyCourseId;
	}

	public void setStudyCourseId(Long studyCourseId) {
		this.studyCourseId = studyCourseId;
	}

	public Long getClassesId() {
		return classesId;
	}

	public void setClassesId(Long classesId) {
		this.classesId = classesId;
	}
        
}
