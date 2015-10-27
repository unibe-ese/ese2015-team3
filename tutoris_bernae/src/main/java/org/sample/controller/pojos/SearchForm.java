package org.sample.controller.pojos;

import java.util.ArrayList;
import java.util.List;

public class SearchForm {
    
    private long id;
    private String courseCriteria;
    private String classCriteria;
    
    public String getCourseCriteria() {
        return courseCriteria;
    }
    
    public void setCourseCriteria(String courseCriteria) {
        this.courseCriteria = courseCriteria;
    }
    
    public String getClassCriteria() {
        return classCriteria;
    }
    
    public void setClassCriteria(String classCriteria) {
        this.classCriteria = classCriteria;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
}
