package edu.rosehulman.reminda.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.rosehulman.reminda.entities.ToDo;
import edu.rosehulman.reminda.entities.ToDoTime;

public class ToDoDataAdapter {
	
	private static ToDoDataAdapter currentInstance;
	protected SQLiteOpenHelper mOpenHelper;
	protected SQLiteDatabase mDB;

	public ToDoDataAdapter(Context context) {
		mOpenHelper = new DBHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
		
	}
	
	public void open(){
		mDB = mOpenHelper.getWritableDatabase();
	}
	
	public void close(){
		mDB.close();
	}
	public static ToDoDataAdapter getCurrentInstance(Context context){
		if(ToDoDataAdapter.currentInstance == null)
			ToDoDataAdapter.currentInstance = new ToDoDataAdapter(context);
		return ToDoDataAdapter.currentInstance;
	}
	
	private ContentValues getContentValuesFromToDo(ToDo todo){
		ContentValues row = new ContentValues();
		row.put(DBHelper.KEY_TITLE, todo.getTitle());
		row.put(DBHelper.KEY_MESSAGE, todo.getMessage());
		row.put(DBHelper.KEY_DATE, todo.getDate());
		row.put(DBHelper.KEY_TIME, todo.getTime());
		return row;
	}
	
	private ContentValues getContentValuesFromToDoTime(ToDoTime toDoTime){
		ContentValues row = new ContentValues();
		row.put(DBHelper.KEY_TODO_ID, toDoTime.getToDoId());
		row.put(DBHelper.KEY_MESSAGE, toDoTime.getMessage());
		row.put(DBHelper.KEY_DURATION, toDoTime.getDuration());
		return row;
	}
	
	public long addToDo(ToDo todo){
		ContentValues row = getContentValuesFromToDo(todo);
		return mDB.insert(DBHelper.TABLE_NAME, null, row);
	}
	
	public long addToDoTime(ToDoTime toDoTime){
		ContentValues row = getContentValuesFromToDoTime(toDoTime);
		return mDB.insert(DBHelper.TODO_TIMES_TABLE, null, row);
	}
	
	public Cursor getToDoCursor(){
		this.open();
		String[] projection = new String[] { DBHelper.KEY_ID, DBHelper.KEY_TITLE, DBHelper.KEY_MESSAGE, DBHelper.KEY_DATE, DBHelper.KEY_TIME };
		return mDB.query(DBHelper.TABLE_NAME, projection, null, null, null, null, DBHelper.KEY_ID + " ASC");
	}
	
	public ToDo getToDo(long id){
		String[] projection = new String[] { DBHelper.KEY_ID, DBHelper.KEY_TITLE, DBHelper.KEY_MESSAGE, DBHelper.KEY_DATE, DBHelper.KEY_TIME };
		String selection = DBHelper.KEY_ID + " = " + id;
		this.open();
		Cursor c = mDB.query(DBHelper.TABLE_NAME, projection, selection, null, null,
				null, null, null);
		if(c != null && c.moveToFirst()){
			ToDo todo = new ToDo();
			todo.setId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_ID)));
			Log.d("REMINDA", "" + todo.getId());
			todo.setTitle(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_TITLE)));
			todo.setMessage(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_MESSAGE)));
			todo.setDate(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_DATE)));
			todo.setTime(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_TIME)));
			todo.setToDoTimes(this.getAllToDoTimesForToDo(todo.getId()));
			this.close();
			return todo;
		}
		Log.d("REMINDA", "NULL");
		return null;
	}
	
	public ToDoTime getToDoTime(long id){
		String [] projection = new String [] { DBHelper.KEY_ID, DBHelper.KEY_TODO_ID, DBHelper.KEY_MESSAGE, DBHelper.KEY_DURATION};
		String selection = DBHelper.KEY_ID + " = " + id;
		this.open();
		Cursor c = mDB.query(DBHelper.TODO_TIMES_TABLE, projection, selection, null, null,
				null, null, null);
		if(c != null && c.moveToFirst()){
			ToDoTime toDoTime = new ToDoTime();
			toDoTime.setId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_ID)));
			toDoTime.setToDoId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_TODO_ID)));
			toDoTime.setMessage(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_MESSAGE)) );
			toDoTime.setDuration(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_DURATION)));
			this.close();
			return toDoTime;
		}
		this.close();
		return null;
	}
	
	public ArrayList<ToDoTime> getAllToDoTimesForToDo(long toDoId){
		ArrayList<ToDoTime> toDoTimes = new ArrayList<ToDoTime>();
		String [] projection = new String [] { DBHelper.KEY_ID, DBHelper.KEY_TODO_ID, DBHelper.KEY_MESSAGE, DBHelper.KEY_DURATION};
		String selection = DBHelper.KEY_TODO_ID + " = " + toDoId;
		this.open();
		Cursor c = mDB.query(DBHelper.TODO_TIMES_TABLE, projection, selection, null, null,
				null, null, null);
		if(c != null && c.moveToFirst()){
			int count = c.getCount();
			for(int i =0; i < count; i++){
				ToDoTime toDoTime = new ToDoTime();
				toDoTime.setId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_ID)));
				toDoTime.setToDoId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_TODO_ID)));
				toDoTime.setMessage(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_MESSAGE)) );
				toDoTime.setDuration(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_DURATION)));
				toDoTimes.add(toDoTime);
				c.moveToNext();
			}
		}
		this.close();
		return toDoTimes;
	}
	
	public ArrayList<ToDo> getAllToDos(){
		ArrayList<ToDo> todos = new ArrayList<ToDo>();
		String[] projection = new String[] { DBHelper.KEY_ID, DBHelper.KEY_TITLE, DBHelper.KEY_MESSAGE, DBHelper.KEY_DATE, DBHelper.KEY_TIME };
		this.open();
		Cursor c = mDB.query(DBHelper.TABLE_NAME, projection, null, null, null,
				null, null, null);
		if(c != null && c.moveToFirst()){
			int count = c.getCount();
			for(int i=0 ; i< count; i++){
				ToDo todo = new ToDo();
				todo.setId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_ID)));
				todo.setTitle(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_TITLE)));
				todo.setMessage(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_MESSAGE)));
				todo.setDate(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_DATE)));
				todo.setTime(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_TIME)));
				todo.setToDoTimes(this.getAllToDoTimesForToDo(todo.getId()));
				todos.add(todo);
				c.moveToNext();
			}
		}
		this.close();
		return todos;
	}
	
	
	public void updateToDo(ToDo todo){
		ContentValues row = getContentValuesFromToDo(todo);
		String selection = DBHelper.KEY_ID + " = " + todo.getId();
		mDB.update(DBHelper.TABLE_NAME, row, selection, null);
	}
	
	public void updateToDoTime(ToDoTime toDoTime){
		ContentValues row = getContentValuesFromToDoTime(toDoTime);
		String selection = DBHelper.KEY_ID + " = " + toDoTime.getId();
		mDB.update(DBHelper.TODO_TIMES_TABLE, row, selection, null);
	}
	
	public boolean removeToDo(ToDo todo){
		return removeToDo(todo.getId());
	}
	public boolean removeToDo(long id){
		String where = DBHelper.KEY_ID + " = " + id;
		return mDB.delete(DBHelper.TABLE_NAME, where, null) > 0;
	}
	
	public boolean removeToDoTime(long id){
		String where = DBHelper.KEY_ID + " = " + id;
		return mDB.delete(DBHelper.TODO_TIMES_TABLE, where, null) > 0;
	}
	
	
	
}
