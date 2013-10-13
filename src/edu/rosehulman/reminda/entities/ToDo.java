package edu.rosehulman.reminda.entities;

public class ToDo {
	private String mTitle;
	private long mId;
	private String mMessage;
	private long mDate;
	private long mTime;
	
	public ToDo(String title, String message, long date, long time) {
		mTitle = title;
		mMessage = message;
		mDate = date;
		mTime = time;
	}

	public ToDo() {
		// TODO Auto-generated constructor stub
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

}
