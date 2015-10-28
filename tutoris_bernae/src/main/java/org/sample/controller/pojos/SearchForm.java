package org.sample.controller.pojos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;

public class SearchForm {
    
    private long id;
    private String studyCourse;
    private String classes;
    private BigDecimal fee;
        
    public String getStudyCourse() {
        return studyCourse;
    }
    
    public void setStudyCourse(String studyCourse) {
        this.studyCourse = studyCourse;
    }
    
    public String getClasses() {
        return classes;
    }
    
    public void setClasses(String classes) {
        this.classes = classes;
    }
    
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
    
}
