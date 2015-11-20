package org.sample.controller.service;

import java.math.BigDecimal;
import java.util.List;

import org.sample.model.CompletedClasses;
import org.sample.model.dao.CompletedClassesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompletedClassesService {
	
	public BigDecimal calculateAverageGrade(List<CompletedClasses> completedClassesList){
		assert(completedClassesList!=null);
		BigDecimal averageGrade = new BigDecimal(0);
		int gradeCount = 0;
		for (CompletedClasses c : completedClassesList){
			BigDecimal nextGrade = c.getGrade();
			if (nextGrade == null) continue;
			averageGrade = averageGrade.add(nextGrade);
			gradeCount++;
		}
		if(gradeCount==0) return null;
		return averageGrade.divide(new BigDecimal(gradeCount));
	}
}
