package org.sample.controller.pojos;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;

/**
 *  Search Form stores filter criterias as class variables.
 *  Available filters are: study course, class and fee.
 */
public class SearchForm {
    
    private long id;

    private Long studyCourseId;
    private Long classesId;
    
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
