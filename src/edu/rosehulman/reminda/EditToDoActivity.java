package edu.rosehulman.reminda;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import edu.rosehulman.reminda.data.DBHelper;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

public class EditToDoActivity extends Activity implements OnClickListener {

	private EditText mName;
	private EditText mMessage;
	private DatePicker mDate;
	private TimePicker mTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_create);
		ToDoDataAdapter adapter = new ToDoDataAdapter(this);
		adapter.open();
		Log.d(RemindaActivity.TAG,
				"id is " + getIntent().getLongExtra(DBHelper.KEY_ID, -1));
		ToDo todo = adapter.getToDo(getIntent().getLongExtra(DBHelper.KEY_ID,
				-1));
		mName = (EditText) findViewById(R.id.name_edit_text);
		mName.setText(todo.getTitle());
		mMessage = (EditText) findViewById(R.id.message_edit_text);
		mMessage.setText(todo.getMessage());
		mDate = (DatePicker) findViewById(R.id.date_picker);
		mDate.updateDate(this.getDatePicker1(todo).getYear(), this
				.getDatePicker1(todo).getMonth(), this.getDatePicker1(todo)
				.getDayOfMonth());
		mTime = (TimePicker) findViewById(R.id.time_picker);
		mTime.setCurrentHour(this.getTimePicker(todo).getCurrentHour());
		mTime.setCurrentMinute(this.getTimePicker(todo).getCurrentMinute());

		((Button) findViewById(R.id.create)).setOnClickListener(this);
		((Button) findViewById(R.id.create)).setText("Save");
		((Button) findViewById(R.id.cancel)).setOnClickListener(this);

		adapter.close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.create:
			if (createToDo()) {
				Intent todoIntent = new Intent(this, RemindaActivity.class);
				setResult(RESULT_OK, todoIntent);
				finish();
			}
			;
			break;

		case R.id.cancel:
			cancelCreateToDo();
			break;
		}

	}

	private void cancelCreateToDo() {
		this.finish();
	}

	private boolean createToDo() {
		if (mName.getText().toString().trim().isEmpty()) {
			showAlert(getString(R.string.warning_empty_name));
			return false;
		}
		ToDo todo = new ToDo(mName.getText().toString(), mMessage.getText()
				.toString(), mDate, mTime);
		todo.setId(getIntent().getLongExtra(DBHelper.KEY_ID, -1));
		ToDoDataAdapter toDoDataAdapter = new ToDoDataAdapter(this);
		toDoDataAdapter.open();
		toDoDataAdapter.updateToDo(todo);
		toDoDataAdapter.close();
		startAlarm(todo);
		return true;
	}

	private void startAlarm(ToDo todo) {

		Intent intentAlarm = new Intent(this, AlarmReceiver.class);
		intentAlarm.putExtra(DBHelper.KEY_TODO_ID, todo.getId());

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// set the alarm for particular time
		PendingIntent p = PendingIntent.getBroadcast(this, (int) todo.getId(),
				intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.set(AlarmManager.RTC_WAKEUP, todo.getAlarmTime(), p);
	}

	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditToDoActivity.this);
		builder.setMessage(message).setCancelable(true)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public DatePicker getDatePicker1(ToDo todo) {
		DatePicker date = new DatePicker(this);
		GregorianCalendar gregCal = new GregorianCalendar();
		gregCal.setTimeInMillis(todo.getDate());
		date.updateDate(gregCal.get(GregorianCalendar.YEAR),
				gregCal.get(GregorianCalendar.MONTH),
				gregCal.get(GregorianCalendar.DAY_OF_MONTH));
		return date;
	}

	public TimePicker getTimePicker(ToDo todo) {
		TimePicker time = new TimePicker(this);
		GregorianCalendar gregCal = new GregorianCalendar();
		gregCal.setTimeInMillis(todo.getAlarmTime());
		time.setCurrentHour(gregCal.get(GregorianCalendar.HOUR_OF_DAY));
		time.setCurrentMinute(gregCal.get(GregorianCalendar.MINUTE));
		return time;
	}

}
