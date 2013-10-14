package edu.rosehulman.reminda;

import android.app.ListFragment;
import android.content.Intent;
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
				 DBHelper.KEY_MESSAGE , DBHelper.KEY_DATE, DBHelper.KEY_TIME};
		 int[] toTextViews = { R.id.todo_title, R.id.todo_message, R.id.todo_date, R.id.todo_time};
		 
		mCursorAdapter = new SimpleCursorAdapter(inflater.getContext(), R.layout.todo_list_item, cursor, fromColumns, toTextViews );
		
		setListAdapter(mCursorAdapter);
		return v;
	}

	@Override
	public void onClick(View v) {
		if (mAddToDoButton.getId() == v.getId()) {
			Intent createToDo = new Intent(getActivity(), CreateToDoActivity.class);
			startActivity(createToDo);
		}

	}

}
