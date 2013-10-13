package edu.rosehulman.reminda.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.rosehulman.reminda.entities.ToDo;

public class ToDoDataAdapter {
	
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
	
	private ContentValues getContentValuesFromToDo(ToDo todo){
		ContentValues row = new ContentValues();
		row.put(DBHelper.KEY_TITLE, todo.getTitle());
		row.put(DBHelper.KEY_MESSAGE, todo.getMessage());
		row.put(DBHelper.KEY_DATE, todo.getDate());
		row.put(DBHelper.KEY_TIME, todo.getTime());
		return row;
	}
	
	public long addToDo(ToDo todo){
		ContentValues row = getContentValuesFromToDo(todo);
		return mDB.insert(DBHelper.TABLE_NAME, null, row);
	}
	
	public Cursor getToDoCursor(){
		String[] projection = new String[] { DBHelper.KEY_ID, DBHelper.KEY_TITLE, DBHelper.KEY_MESSAGE, DBHelper.KEY_DATE, DBHelper.KEY_TIME };
		return mDB.query(DBHelper.TABLE_NAME, projection, null, null, null, null, DBHelper.KEY_ID + " ASC");
	}
	
	public ToDo getToDo(long id){
		String[] projection = new String[] { DBHelper.KEY_ID, DBHelper.KEY_TITLE, DBHelper.KEY_MESSAGE, DBHelper.KEY_DATE, DBHelper.KEY_TIME };
		String selection = DBHelper.KEY_ID + " = " + id;
		Cursor c = mDB.query(DBHelper.TABLE_NAME, projection, selection, null, null,
				null, null, null);
		if(c != null && c.moveToFirst()){
			ToDo todo = new ToDo();
			todo.setId(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_ID)));
			todo.setTitle(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_TITLE)));
			todo.setMessage(c.getString(c.getColumnIndexOrThrow(DBHelper.KEY_MESSAGE)));
			todo.setDate(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_DATE)));
			todo.setTime(c.getLong(c.getColumnIndexOrThrow(DBHelper.KEY_TIME)));
			
			return todo;
		}
		return null;
	}
	
	public void updateToDo(ToDo todo){
		ContentValues row = getContentValuesFromToDo(todo);
		String selection = DBHelper.KEY_ID + " = " + todo.getId();
		mDB.update(DBHelper.TABLE_NAME, row, selection, null);
	}
	
	public boolean removeToDo(ToDo todo){
		return removeToDo(todo.getId());
	}
	public boolean removeToDo(long id){
		String where = DBHelper.KEY_ID + " = " + id;
		return mDB.delete(DBHelper.TABLE_NAME, where, null) > 0;
	}

	
}
