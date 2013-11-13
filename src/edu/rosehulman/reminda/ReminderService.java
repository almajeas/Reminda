package edu.rosehulman.reminda;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;
import edu.rosehulman.reminda.data.ToDoDataAdapter;

public class ReminderService extends IntentService {

	private long todoId;
	private ToDoDataAdapter mToDoDataAdapter;

    public ReminderService(long todoId){
        super("ReminderService");
        this.todoId = todoId;
        mToDoDataAdapter = ToDoDataAdapter.getCurrentInstance(this);
    }

    @Override
      protected void onHandleIntent(Intent intent) {
    	Toast.makeText(this, "It works", Toast.LENGTH_LONG).show();
    	/*this.todoId = intent.getIntExtra("ToDoID", 0);
    	ToDo todo = mToDoDataAdapter.getToDo(todoId);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(this)
        .setContentTitle(todo.getTitle())
        .setContentText(todo.getMessage())
        .setSmallIcon(R.drawable.reminda_notification)
        .setAutoCancel(true)
        .build();
        nm.notify((int) todo.getId(), n);*/
    }

}
