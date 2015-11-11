package org.sample.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.sample.model.Classes;

public class ClassesListWrapper {

	private List<Classes> classesList;
	
	public List<Classes> getClassesList() {
		return classesList;
	}

	public void setClassesList(List<Classes> classesList) {
		this.classesList = classesList;
	}

	public ClassesListWrapper(){
		this.classesList = new ArrayList<Classes>();
	}
	
	public void add(Classes c) {
		this.classesList.add(c);
	}
	
	public void remove(Classes c){
		this.classesList.remove(c);
	}
	
	public void remove(int i){
		this.classesList.remove(i);
	}
	
	public boolean  contains(Classes c){
		return this.classesList.contains(c);
	}
}
