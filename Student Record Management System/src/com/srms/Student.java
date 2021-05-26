package com.srms;

import java.util.HashMap;

public class Student {
	private String name;
	private String section;

	private Subject[] subjects;
	private HashMap<Subject, Double> grades;
	
	public Student(String name, String section, Subject[] subjects,
			HashMap<String, String> schedules) {
		super();
		this.name = name;
		this.section = section;
		this.subjects = subjects;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSection() {
		return section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}

	public Subject[] getSubjects() {
		return subjects;
	}

	public HashMap<Subject, Double> getGrades() {
		return grades;
	}

	public void setSubjects(Subject[] subjects) {
		this.subjects = subjects;
	}

	public void setGrades(HashMap<Subject, Double> grades) {
		this.grades = grades;
	}
	
	
}
