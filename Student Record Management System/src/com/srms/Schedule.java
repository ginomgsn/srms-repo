package com.srms;

public class Schedule {
	String timeStart;
	String timeEnd;
	String day;
	
	public Schedule(String timeStart, String timeEnd, String day) {
		super();
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.day = day;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public String getDay() {
		return day;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return timeStart + "/" + timeEnd + " " + day;
	}
	
	
}
