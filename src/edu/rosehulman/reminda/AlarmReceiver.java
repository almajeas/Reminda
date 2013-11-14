package edu.rosehulman.reminda;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import edu.rosehulman.reminda.data.DBHelper;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

public class AlarmReceiver extends BroadcastReceiver {

	private ToDoDataAdapter mToDoDataAdapter;

	public AlarmReceiver() {

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		mToDoDataAdapter = ToDoDataAdapter.getCurrentInstance(context);
		Bundle b = intent.getExtras();
		long id = b.getLong(DBHelper.KEY_TODO_ID, -1);
		ToDo todo = mToDoDataAdapter.getToDo(id);
		triggerNotification(todo, context);

	}

	private void triggerNotification(ToDo todo, Context context) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder mBuilder = new Notification.Builder(context)
				.setContentTitle(todo.getTitle())
				.setContentText(todo.getMessage())
				.setSmallIcon(R.drawable.reminda_notification)
				.setAutoCancel(true)
				.setVibrate(new long[] { 100, 300, 100, 300 });

		Intent resultIntent = new Intent(context, RemindaActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(RemindaActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);

		nm.notify((int) todo.getId(), mBuilder.build());
	}

}
