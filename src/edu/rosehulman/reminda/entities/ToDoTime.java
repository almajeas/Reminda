package edu.rosehulman.reminda.entities;

public class ToDoTime {

	private long mId, mToDoId, mDuration;
	private String mMessage;
	
	public ToDoTime(long id, long toDoId, String message, long duration){
		this.setId(id);
		this.setToDoId(toDoId);
		this.setMessage(message);
		this.setDuration(duration);
	}

	public long getId() {
		return mId;
	}

	public void setId(long mId) {
		this.mId = mId;
	}

	public long getToDoId() {
		return mToDoId;
	}

	public void setToDoId(long mToDoId) {
		this.mToDoId = mToDoId;
	}

	public long getDuration() {
		return mDuration;
	}

	public void setDuration(long mDuration) {
		this.mDuration = mDuration;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}
}
