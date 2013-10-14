package edu.rosehulman.reminda;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import edu.rosehulman.reminda.data.DBHelper;
import edu.rosehulman.reminda.data.ToDoDataAdapter;

public class ToDoActivity extends ListFragment implements OnClickListener {

	private Button mAddToDoButton;
	private ToDoDataAdapter mToDoDataAdapter;
	private SimpleCursorAdapter mCursorAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_todo, container, false);
		mAddToDoButton = (Button) v.findViewById(R.id.addToDoButton);
		mAddToDoButton.setOnClickListener(this);

		mToDoDataAdapter = new ToDoDataAdapter(getActivity());
		mToDoDataAdapter.open();

		Cursor cursor = mToDoDataAdapter.getToDoCursor();
		String[] fromColumns = new String[] { DBHelper.KEY_TITLE,
				DBHelper.KEY_MESSAGE };
		int[] toTextViews = { R.id.todo_title_id, R.id.todo_message_id };
		mCursorAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.activity_todo, cursor, fromColumns, toTextViews);
		setListAdapter(mCursorAdapter);
		registerForContextMenu(getListView());
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
