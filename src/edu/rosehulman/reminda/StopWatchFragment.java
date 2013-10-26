package edu.rosehulman.reminda;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchFragment extends Fragment implements OnClickListener {
	private enum STATE {FRESH, RUNNING, PAUSED};
	private STATE mState;
	private TextView mTimeTextView;
	private Button mStartButton, mResetButton;
	private long mTime = 0;
	private long mStartTime = 0;
	private long mTotalTime = 0;
	private long mTimeSwap = 0;
	private Handler mHandler;
	private Runnable updateTimer = new Runnable(){
		public void run(){
			mTime = SystemClock.uptimeMillis() - mStartTime;
			mTotalTime = mTimeSwap + mTime;
			timeDisplay();
			mHandler.postDelayed(this, 0);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_stop_watch, container, false);
		mState = STATE.FRESH;
		mTimeTextView = (TextView) v.findViewById(R.id.timeTextView);
		mStartButton = (Button) v.findViewById(R.id.StartButton);
		mStartButton.setOnClickListener(this);
		mResetButton = (Button) v.findViewById(R.id.resetButton);
		mResetButton.setOnClickListener(this);
		mResetButton.setEnabled(false);
		mHandler = new Handler();
		return v;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.StartButton:
			if(mState == STATE.FRESH || mState == STATE.PAUSED){
				mStartTime = SystemClock.uptimeMillis();
				mState = STATE.RUNNING;
				mResetButton.setEnabled(true);
				mStartButton.setText(getResources().getString(R.string.pause));
				mHandler.postDelayed(updateTimer, 0);
			}else if(mState == STATE.RUNNING){
				mState = STATE.PAUSED;
				mStartButton.setText(getString(R.string.start));
				mTimeSwap = mTotalTime;
				mHandler.removeCallbacks(updateTimer);
			}
			break;
			
		case R.id.resetButton:
			mState = STATE.FRESH;
			mHandler.removeCallbacks(updateTimer);
			mResetButton.setEnabled(false);
			mStartButton.setText(getString(R.string.start));
			mTime = 0;
			mStartTime = 0;
			mTotalTime = 0;
			mTimeSwap = 0;
			timeDisplay();
			break;
		}
		
	}
	
	private void timeDisplay() {
		int seconds = (int) (mTotalTime/1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		int milliseconds = (int) (mTotalTime %1000);
		mTimeTextView.setText(String.format("%02d:%02d:%03d",minutes,seconds,milliseconds ));
	}
}
