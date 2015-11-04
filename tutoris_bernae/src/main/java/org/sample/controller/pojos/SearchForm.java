package org.sample.controller.pojos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;

public class SearchForm {
    
    private long id;

    private Long studyCourseId;
    private Long classesId;
    private BigDecimal fee;

    
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
