package edu.rosehulman.reminda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

public class AlarmReceiver extends BroadcastReceiver {

	private ToDoDataAdapter mToDoDataAdapter;
	
	public AlarmReceiver() {

	}
	

	@Override
	public void onReceive(Context context, Intent intent) {
		mToDoDataAdapter = ToDoDataAdapter.getCurrentInstance(context);
		ToDo todo = mToDoDataAdapter.getAllToDos().get(intent.getIntExtra("ToDoID", -1));
		Toast.makeText(context, "Got ToDo with title " + todo.getTitle(), Toast.LENGTH_LONG).show();

	}

}
