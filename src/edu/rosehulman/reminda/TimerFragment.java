package edu.rosehulman.reminda;

import java.util.ArrayList;

import edu.rosehulman.reminda.data.ToDoAdapter;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.data.ToDoScrollAdapter;
import edu.rosehulman.reminda.entities.ToDo;
import edu.rosehulman.reminda.entities.ToDoTime;
import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TimerFragment extends Fragment implements OnClickListener {
	private Context mContext;
	private Button mStartButton, mCancelButton;
	private NumberPicker mHr, mMin, mSec;
	private EditText mMessage;
	private TextView mTimeText;
	private CountDownTimer cdt;
	private long mTimeStart;
	private long mTimeLeft;
	private int mNotificationId = 499;
	private Spinner mToDosSpinner;
	private ToDoDataAdapter mToDoDataAdapter;
	NotificationManager mNotificationManager;
	private boolean isFragmentAlive = false;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_timer, container, false);

		isFragmentAlive = true;
		mStartButton = (Button) v.findViewById(R.id.startButtonTimer);
		mCancelButton = (Button) v.findViewById(R.id.cancelButtonTimer);
		mTimeText = (TextView) v.findViewById(R.id.timer_text_view);
		mHr = (NumberPicker) v.findViewById(R.id.long_hr);
		mHr.setMaxValue(24);
		mHr.setMinValue(0);
		mMin = (NumberPicker) v.findViewById(R.id.long_min);
		mMin.setMaxValue(59);
		mMin.setMinValue(0);
		mSec = (NumberPicker) v.findViewById(R.id.long_sec);
		mSec.setMaxValue(59);
		mSec.setMinValue(0);
		mMessage = (EditText) v.findViewById(R.id.timer_note);

		mStartButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		mCancelButton.setEnabled(false);

		mNotificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mToDosSpinner = (Spinner) v.findViewById(R.id.todos_spinner);
		mToDoDataAdapter = ToDoDataAdapter.getCurrentInstance(getActivity());
		ArrayList<ToDo> todos = mToDoDataAdapter.getAllToDos();
		ToDoAdapter adapter = new ToDoScrollAdapter(todos, getActivity());
		mToDosSpinner.setAdapter(adapter);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.startButtonTimer):
			mTimeStart = 1000 * ((mSec.getValue()) + (mMin.getValue() * 60) + (mHr
					.getValue() * 60 * 60));
			cdt = new CountDownTimer(mTimeStart, 10) {

				@Override
				public void onTick(long millisUntilFinished) {
					mTimeLeft = millisUntilFinished / 1000;
					int hours, minutes, seconds;
					seconds = (int) (millisUntilFinished / 1000);
					minutes = seconds / 60;
					hours = minutes / 60;
					seconds = seconds % 60;
					minutes = minutes % 60;
					String mTimeString = String.format("%02d:%02d:%02d", hours,
							minutes, seconds);
					mTimeText.setText(mTimeString);
					if (!isFragmentAlive) {
						triggerNotification();
					}

				}

				@Override
				public void onFinish() {
					Toast.makeText(getActivity(), "Timer is done",
							Toast.LENGTH_LONG).show();
					triggerNotification();
					addSprint();
					mMessage.setEnabled(true);
					mToDosSpinner.setEnabled(true);
					mStartButton.setEnabled(true);
					mCancelButton.setEnabled(false);

				}

				
			};
			cdt.start();
			mMessage.setEnabled(false);
			mToDosSpinner.setEnabled(false);
			mStartButton.setEnabled(false);
			mCancelButton.setEnabled(true);
			break;
		case (R.id.cancelButtonTimer):
			addSprint();
			cdt.cancel();
			mMessage.setEnabled(true);
			mToDosSpinner.setEnabled(true);
			mStartButton.setEnabled(true);
			mCancelButton.setEnabled(false);
			break;
		}

	}

	private void addSprint() {
		ToDo selectedDo = (ToDo) mToDosSpinner.getSelectedItem();
		ToDoTime tdt = new ToDoTime(-1, selectedDo.getId(), mMessage.getText()
				.toString(), getSpentTimeinSeconds());
		mToDoDataAdapter.addToDoTime(tdt);
	}
	private void triggerNotification() {
		Notification.Builder mBuilder = new Notification.Builder(getActivity())
				.setSmallIcon(R.drawable.reminda_notification)
				.setContentTitle("Reminda Timer")
				.setContentText(
						mMessage.getText().toString() + "\n"
								+ mTimeText.getText().toString())
				.setVibrate(new long[] { 100, 100, 100, 100 });
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(mContext, RemindaActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(RemindaActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mNotificationId, mBuilder.build());
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart() {
		// Toast.makeText(getActivity(), "Timer view is onStart",
		// Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		// Toast.makeText(getActivity(), "Timer view is onResume",
		// Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		isFragmentAlive = false;
		Toast.makeText(getActivity(), "Timer view is onPause",
				Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), "Timer view is onStop",
		// Toast.LENGTH_LONG).show();
		super.onStop();
	}

	@Override
	public void onDestroyView() {

		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), "Timer view is destroyed",
		// Toast.LENGTH_LONG).show();
		super.onDestroyView();

	}

	private long getSpentTimeinSeconds() {
		return mTimeStart - mTimeLeft;
	}

}
