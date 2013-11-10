package edu.rosehulman.reminda;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import edu.rosehulman.reminda.data.DBHelper;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;
import edu.rosehulman.reminda.entities.ToDoTime;

public class ShowToDoActivity extends Activity {

	private TextView title;
	private TextView message;
	private TextView date;
	private ScrollView scrollTimes;
	private ToDo todo;
	private ArrayList<ToDoTime> times = new ArrayList<ToDoTime>();
	private LinearLayout linLay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_view);
		ToDoDataAdapter adapter = ToDoDataAdapter.getCurrentInstance(this);
		linLay = (LinearLayout) findViewById(R.id.todoViewScrollLinLay);
		adapter.open();
		todo = adapter.getToDo(getIntent().getLongExtra(DBHelper.KEY_ID,
				-1));
		title = (TextView) findViewById(R.id.todoViewTitle);
		message = (TextView) findViewById(R.id.todoViewMessage);
		date = (TextView) findViewById(R.id.todoViewDate);
		title.setText(todo.getTitle());
		message.setText(todo.getMessage());
		date.setText(todo.getStringDate() + " " + todo.getStringTime());
		times = todo.getToDoTimes();
		for (int i = 0; i < times.size(); i++) {
			addTimes(i);
		}
	}

	private void addTimes(int i) {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout item = (LinearLayout) inflater.inflate(
				R.layout.todo_timer_item, null);
		int[] counterDepth = { 0 };
		int[] messageDepth = { 1, 0 };
		int[] timeDepth = { 1, 1 };

		TextView tCounter = (TextView) expandTV(item, counterDepth);
		TextView tMessage = (TextView) expandTV(item, messageDepth);
		TextView tTime = (TextView) expandTV(item, timeDepth);
		tCounter.setText(String.format("%d.", i));

		tMessage.setText(times.get(i).getMessage());
		tTime.setText(times.get(i).getDuration() + "");
		
		linLay.addView(item);
		linLay.refreshDrawableState();

	}

	private View expandTV(LinearLayout root, int[] depths) {
		for (int i = 0; i < depths.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depths[i]);
		}
		return root.getChildAt(depths[depths.length - 1]);
	}

}
