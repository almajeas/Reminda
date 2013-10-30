package edu.rosehulman.reminda.entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.Duration;

import android.widget.DatePicker;
import android.widget.TimePicker;

public class ToDo {
	private String mTitle;
	private long mId;
	private String mMessage;
	private long mDate; // days since jan1 1970
	private long mTime; // min since midnight
	private ArrayList<Duration> durations;

	public ToDo(String title, String message, long date, long time) {
		mTitle = title;
		mMessage = message;
		mDate = date;
		mTime = time;
	}
	public ToDo(String title, String message, long date, long time, ArrayList<Duration> durations) {
		this(title, message, date, time);
		this.durations = durations;
	}

	public ToDo(String title, String message, DatePicker date, TimePicker time) {
		mTitle = title;
		mMessage = message;
		setDateToLong(date, time);
	}

	public ToDo() {
		durations = new ArrayList<Duration>();
		// TODO Auto-generated constructor stub
	}

	private void setDateToLong(DatePicker date, TimePicker time) {
		GregorianCalendar newDate = new GregorianCalendar(date.getYear(),
				date.getMonth(), date.getDayOfMonth());
		mDate = newDate.getTimeInMillis();
		newDate = new GregorianCalendar(0, 0, 0, time.getCurrentHour(),
				time.getCurrentMinute());
		mTime = newDate.getTimeInMillis();
	}
	
	public String getStringDate(){
		Date date = new Date(this.getDate());
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(date);
	}
	
	public String getStringTime(){
		Time t = new Time(this.getTime());
		return t.toString();
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public long getId() {
		return mId;
	}

	public void setId(long mId) {
		this.mId = mId;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long mDate) {
		this.mDate = mDate;
	}

	public long getTime() {
		return mTime;
	}

	public void setTime(long mTime) {
		this.mTime = mTime;
	}

	public ArrayList<Duration> getDurations() {
		return durations;
	}

	public void setDurations(ArrayList<Duration> durations) {
		this.durations = durations;
	}

}
