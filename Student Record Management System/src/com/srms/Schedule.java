package com.srms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
	String timeStart;
	String timeEnd;
	String day;
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
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
	    Date timeStart = null;
	    Date timeEnd = null;
	    
	    try {
	        timeStart = sdf.parse(this.timeStart);
	        timeEnd = sdf.parse(this.timeEnd);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    String fTimeStart = sdf.format(timeStart);
	    String fTimeEnd = sdf.format(timeEnd);

		return fTimeStart + "/" + fTimeEnd + " " + day;
	}
	
	
}
