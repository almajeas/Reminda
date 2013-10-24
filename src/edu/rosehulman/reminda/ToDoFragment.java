package edu.rosehulman.reminda;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import edu.rosehulman.reminda.data.DBHelper;
import edu.rosehulman.reminda.data.ToDoAdapter;
import edu.rosehulman.reminda.data.ToDoDataAdapter;
import edu.rosehulman.reminda.entities.ToDo;

public class ToDoFragment extends ListFragment implements OnClickListener {

	private Button mAddToDoButton;

	private ToDoDataAdapter mToDoDataAdapter;
	
	protected static final int REQUEST_CODE_CREATE_TODO = 1;
	protected static final int REQUEST_CODE_EDIT_TODO = 2;
	
	private static final int EDIT = 1;
	private static final int DELETE = 2;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_todo, container, false);
		mAddToDoButton = (Button) v.findViewById(R.id.addToDoButton);
		mAddToDoButton.setOnClickListener(this);
		mToDoDataAdapter = new ToDoDataAdapter(getActivity());
		mToDoDataAdapter.open();
		populate();
		return v;
	}
	
	private void populate(){
		ArrayList<ToDo> todos = mToDoDataAdapter.getAllToDos();
		ToDoAdapter adapter = new ToDoAdapter(todos, this.getActivity());
		setListAdapter(adapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());
	}



	@Override
	public void onClick(View v) {
		if (mAddToDoButton.getId() == v.getId()) {
			Intent createToDo = new Intent(getActivity(), CreateToDoActivity.class);
			this.startActivityForResult(createToDo, REQUEST_CODE_CREATE_TODO);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Options");
		menu.add(0, DELETE, 0, "Delete");
		menu.add(0, EDIT, 0, "Edit");
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent editIntent = new Intent(getActivity(), EditToDoActivity.class);
		editIntent.putExtra(DBHelper.KEY_ID, id);
		this.startActivityForResult(editIntent, REQUEST_CODE_EDIT_TODO);
	}
	
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// Request code will tell you which activity is returning with a result
			Log.d(RemindaActivity.TAG, "onResult is called");
//			Cursor c = mToDoDataAdapter.getToDoCursor();
//			mCursorAdapter.changeCursor(c);
			populate();
	    	
		}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int pos = info.position;
		long id = info.id;
		Log.d(RemindaActivity.TAG, "selected Item id: " + id);
		Log.d(RemindaActivity.TAG, "selected Item pos: " + pos);
		if (item.getItemId() == DELETE) {
			mToDoDataAdapter.open();
			mToDoDataAdapter.removeToDo(id);
			mToDoDataAdapter.close();
			
		}else if(item.getItemId() == EDIT){
			Intent editIntent = new Intent(getActivity(), EditToDoActivity.class);
			editIntent.putExtra(DBHelper.KEY_ID, id);
			this.startActivityForResult(editIntent, REQUEST_CODE_EDIT_TODO);	
		}
		populate();
		return super.onContextItemSelected(item);
	}
	

}
