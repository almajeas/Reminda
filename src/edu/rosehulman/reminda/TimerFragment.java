package edu.rosehulman.reminda;

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
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class TimerFragment extends Fragment implements OnClickListener {
	private Context mContext;
	private Button mStartButton, mCancelButton;
	private NumberPicker mHr, mMin, mSec;
	private EditText mMessage;
	private TextView mTimeText;
	private Handler mHandler;
	private CountDownTimer cdt;
	private long mTimeLeft;
	private int mNotificationId = 499;
	

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

		mHandler = new Handler();
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.startButtonTimer):
			mTimeLeft = 1000 * ((mSec.getValue()) + (mMin.getValue() * 60) + (mHr
					.getValue() * 60 * 60));
			cdt = new CountDownTimer(mTimeLeft, 10) {

				@Override
				public void onTick(long millisUntilFinished) {
					int hours, minutes, seconds;
					seconds = (int) (millisUntilFinished / 1000);
					minutes = seconds / 60;
					hours = minutes / 60;
					seconds = seconds % 60;
					minutes = minutes % 60;
					mTimeText.setText(String.format("%02d:%02d:%02d", hours,
							minutes, seconds));

				}

				@Override
				public void onFinish() {
					Toast.makeText(getActivity(), "Timer is done", Toast.LENGTH_LONG).show();
					triggerNotification();
					mStartButton.setEnabled(true);
					mCancelButton.setEnabled(false);

				}
			};
			cdt.start();
			mStartButton.setEnabled(false);
			mCancelButton.setEnabled(true);
			break;
		case (R.id.cancelButtonTimer):
			cdt.cancel();
			mStartButton.setEnabled(true);
			mCancelButton.setEnabled(false);
			break;
		}

	}
	
	private void triggerNotification(){
		Notification.Builder mBuilder =
		        new Notification.Builder(getActivity())
		        .setSmallIcon(R.drawable.reminda_notification)
		        .setContentTitle("Reminda Timer")
		        .setContentText("Timer Ended");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(mContext, RemindaActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(RemindaActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mNotificationId, mBuilder.build());
		
		
	}

}
