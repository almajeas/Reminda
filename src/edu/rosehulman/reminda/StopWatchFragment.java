package edu.rosehulman.reminda;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import edu.rosehulman.reminda.data.ToDoAdapter;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

public class StopWatchFragment extends Fragment implements OnClickListener {
	private enum STATE {
		FRESH, RUNNING, PAUSED
	};
	
	private Spinner mToDosSpinner;
	private ToDoDataAdapter mToDoDataAdapter;
	private STATE mState;
	private Context mContext;
	private TextView mTimeTextView;
	private Button mStartButton, mResetButton, mLapButton;
	private LinearLayout mLapsLayout;
	private ScrollView mLapsScrollView;
	private long mTime = 0;
	private long mStartTime = 0;
	private long mTotalTime = 0;
	private long mTimeSwap = 0;
	private int mLapsCounter = 1;
	private long mLapTime = 0;
	private Handler mHandler;
	private Runnable updateTimer = new Runnable() {
		public void run() {
			mTime = SystemClock.uptimeMillis() - mStartTime;
			mTotalTime = mTimeSwap + mTime;
			mTimeTextView.setText(timeFormatString(mTotalTime));
			mHandler.postDelayed(this, 0);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_stop_watch, container, false);
		mContext = this.getActivity();
		mState = STATE.FRESH;
		mTimeTextView = (TextView) v.findViewById(R.id.timeTextView);
		mStartButton = (Button) v.findViewById(R.id.startButton);
		mStartButton.setOnClickListener(this);
		mResetButton = (Button) v.findViewById(R.id.resetButton);
		mResetButton.setOnClickListener(this);
		mResetButton.setEnabled(false);
		mLapButton = (Button) v.findViewById(R.id.lapButton);
		mLapButton.setOnClickListener(this);
		mLapsLayout = (LinearLayout) v.findViewById(R.id.lapsLayout);
		mLapsScrollView = (ScrollView) v.findViewById(R.id.lapsScrollView);
		mHandler = new Handler();
		mToDosSpinner = (Spinner) v.findViewById(R.id.todos_spinner);
		mToDoDataAdapter = ToDoDataAdapter.getCurrentInstance(getActivity());
		ArrayList<ToDo> todos = mToDoDataAdapter.getAllToDos();
		ToDoAdapter adapter = new ToDoAdapter(todos, getActivity());
		mToDosSpinner.setAdapter(adapter);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startButton:
			if (mState == STATE.FRESH || mState == STATE.PAUSED) {
				mStartTime = SystemClock.uptimeMillis();
				mState = STATE.RUNNING;
				mResetButton.setEnabled(true);
				mStartButton.setText(getResources().getString(R.string.pause));
				mHandler.postDelayed(updateTimer, 0);
			} else if (mState == STATE.RUNNING) {
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
			mLapsCounter = 1;
			mLapTime = 0;
			mTimeTextView.setText(timeFormatString(mTotalTime));
			mLapsLayout.removeAllViews();
			break;
		case R.id.lapButton:
			mResetButton.setEnabled(true);
			addLap();
			break;
		}

	}

	private void addLap() {
		LayoutInflater inflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout item = (LinearLayout) inflater.inflate(R.layout.lap_item,
				null);
		int[] counterDepth = { 0 };
		int[] timeDepth = { 2 };

		TextView tCounter = (TextView) expandTV(item, counterDepth);
		TextView tTime = (TextView) expandTV(item, timeDepth);
		tCounter.setText(String.format("%d.", mLapsCounter++));

		mLapTime = mTotalTime - mLapTime;
		tTime.setText(timeFormatString(mLapTime));
		mLapTime = mTotalTime;
		mLapsLayout.addView(item);
		mLapsLayout.refreshDrawableState();
		mLapsScrollView.fullScroll(View.FOCUS_DOWN);
	}

	private View expandTV(LinearLayout root, int[] depths) {
		for (int i = 0; i < depths.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depths[i]);
		}
		return root.getChildAt(depths[depths.length - 1]);
	}

	private String timeFormatString(long time) {
		int seconds = (int) (time / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		int milliseconds = (int) (time % 1000);
		return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
	}
}
