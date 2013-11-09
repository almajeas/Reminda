package edu.rosehulman.reminda.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	protected static final String TAG = "TAG"; // Just the tag we use to log

	protected static final String DATABASE_NAME = "reminda.db"; // Becomes the
																// filename of
																// the database
	protected static final String TABLE_NAME = "todos"; 
	protected static final String TODO_TIMES_TABLE = "todo_times_table";
	
	protected static final int DATABASE_VERSION = 1; // We increment this every
													// time we change the
													// database schema
													// which will kick off an
													// automatic upgrade

	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	
	public static final String KEY_TODO_ID = "todo_id";
	public static final String KEY_DURATION = "duration";
	
	public static final String DROP_STATEMENT = "DROP TABLE IF EXISTS"
			+ TABLE_NAME +" , "+ TODO_TIMES_TABLE;

	public static String CREATE_STATEMENT;
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_NAME + "(");
		sb.append(KEY_ID + " integer primary key autoincrement, ");
		sb.append(KEY_TITLE + " text, ");
		sb.append(KEY_MESSAGE + " text, ");
		sb.append(KEY_DATE + " long, ");
		sb.append(KEY_TIME+ " long");
		sb.append(")");
		CREATE_STATEMENT = sb.toString();

	}
	
	public static String CREATE_TODO_TIMES_TABLE;
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_NAME + "(");
		sb.append(KEY_ID + " integer primary key autoincrement, ");
		sb.append(KEY_TODO_ID+ "long, ");
		sb.append(KEY_MESSAGE + " text, ");
		sb.append(KEY_DURATION + "long");
		sb.append(")");
		CREATE_TODO_TIMES_TABLE = sb.toString();
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STATEMENT);
		db.execSQL(CREATE_TODO_TIMES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgradin db from " + oldVersion + " to " + newVersion
				+ " which will destroy the data");
		db.execSQL(DROP_STATEMENT);
		onCreate(db);
	}

}
