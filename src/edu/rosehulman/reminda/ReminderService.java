package edu.rosehulman.reminda;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

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
    	ToDo todo = mToDoDataAdapter.getToDo(todoId);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(this)
        .setContentTitle(todo.getTitle())
        .setContentText(todo.getMessage())
        .setSmallIcon(R.drawable.reminda_notification)
        .setAutoCancel(true)
        .build();
        nm.notify((int) todo.getId(), n);
    }

}
