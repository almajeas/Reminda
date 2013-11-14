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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import edu.rosehulman.reminda.data.DBHelper;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

public class CreateToDoActivity extends Activity implements OnClickListener {

	private EditText mName;
	private EditText mMessage;
	private DatePicker mDate;
	private TimePicker mTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_create);

		mName = (EditText) findViewById(R.id.name_edit_text);
		mMessage = (EditText) findViewById(R.id.message_edit_text);
		mDate = (DatePicker) findViewById(R.id.date_picker);
		mTime = (TimePicker) findViewById(R.id.time_picker);

		((Button) findViewById(R.id.create)).setOnClickListener(this);
		((Button) findViewById(R.id.cancel)).setOnClickListener(this);

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

	private void startAlarm(ToDo todo) {

		Intent intentAlarm = new Intent(this, AlarmReceiver.class);
		intentAlarm.putExtra(DBHelper.KEY_TODO_ID, todo.getId());

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// set the alarm for particular time
		PendingIntent p = PendingIntent.getBroadcast(this, (int) todo.getId(),
				intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);

		alarmManager.set(AlarmManager.RTC_WAKEUP, todo.getAlarmTime(), p);
	}

	private boolean createToDo() {
		if (mName.getText().toString().trim().isEmpty()) {
			showAlert(getString(R.string.warning_empty_name));
			return false;
		}
		ToDo todo = new ToDo(mName.getText().toString(), mMessage.getText()
				.toString(), mDate, mTime);
		ToDoDataAdapter toDoDataAdapter = new ToDoDataAdapter(this);
		toDoDataAdapter.open();
		long value = toDoDataAdapter.addToDo(todo);
		toDoDataAdapter.close();
		if (value > 0) {
			todo.setId(value);
			startAlarm(todo);
			return true;
		}
		return false;
	}

	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CreateToDoActivity.this);
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

}
