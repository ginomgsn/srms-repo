package com.srms;

public class Subject {
	private String name;
	private String code;
	private Schedule schedule;
	
	public Subject(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}
	
	public Subject(String name, String code, Schedule schedule) {
		super();
		this.name = name;
		this.code = code;
		this.schedule = schedule;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
