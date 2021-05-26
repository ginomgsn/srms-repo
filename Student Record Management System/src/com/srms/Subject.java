package com.srms;

import java.util.ArrayList;
import java.util.List;

public class Subject {
	private String name;
	private String code;
	private List<String> schedules;
	private List<String> professors;
	
	public Subject(String name, String code) {
		super();
		this.name = name;
		this.code = code;
		this.schedules = new ArrayList<String>();
		this.professors = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public List<String> getSchedules() {
		return schedules;
	}
	public List<String> getProfessors() {
		return professors;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setSchedules(List<String> schedules) {
		this.schedules = schedules;
	}
	public void setProfessors(List<String> professors) {
		this.professors = professors;
	}
	
	public void addProfessor(String name) {
		this.professors.add(name);
	}
	
	public void addSchedule(String time) {
		this.schedules.add(time);
	}
	
	public void removeProfessor(String name) {
		this.professors.remove(name);
	}
	
	public void removeSchedule(String time) {
		this.schedules.remove(time);
	}
}
