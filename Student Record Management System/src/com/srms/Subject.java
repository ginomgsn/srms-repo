package com.srms;

public class Subject {
	private String name;
	private String code;
	
	public Subject(String name, String code) {
		super();
		this.name = name;
		this.code = code;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
