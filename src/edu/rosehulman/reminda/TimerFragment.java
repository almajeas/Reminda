package edu.rosehulman.reminda;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

	Button mStartButton, mCancelButton;
	NumberPicker mHr, mMin, mSec;
	EditText mMessage;
	TextView mTimeText;
	Handler mHandler;
	CountDownTimer cdt;
	long mTimeLeft;

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

}
