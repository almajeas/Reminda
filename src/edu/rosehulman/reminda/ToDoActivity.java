package edu.rosehulman.reminda;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ToDoActivity extends ListFragment implements OnClickListener {

	private Button mAddToDoButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_todo, container, false);
		mAddToDoButton = (Button) v.findViewById(R.id.addToDoButton);
		mAddToDoButton.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		if (mAddToDoButton.getId() == v.getId()) {
			Toast.makeText(getActivity(), "Clicked add TODO",
					Toast.LENGTH_SHORT).show();
		}

	}

}
