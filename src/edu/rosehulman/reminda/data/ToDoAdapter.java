package edu.rosehulman.reminda.data;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.rosehulman.reminda.R;
import edu.rosehulman.reminda.entities.ToDo;

public class ToDoAdapter extends BaseAdapter {
	
	private ArrayList<ToDo> todos;
	private Context mContext;
	private LayoutInflater inflater;

	public ToDoAdapter(ArrayList<ToDo> todos, Context c){
		this.todos = todos;
		this.mContext = c;
	}
	@Override
	public int getCount() {
		return todos.size();
	}

	@Override
	public ToDo getItem(int position) {
		return this.todos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.todos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout item = (LinearLayout) inflater.inflate(R.layout.todo_list_item, null);
		int [] titleDepth = {0};
		int [] messageDepth = {1};
		int [] dateDepth = {2};
		int [] timeDepth = {3};
		TextView tTitle = (TextView) expandTV(item, titleDepth);
		TextView tMessage = (TextView) expandTV(item, messageDepth);
		TextView tDate = (TextView) expandTV(item, dateDepth);
		TextView tTime = (TextView) expandTV(item, timeDepth);	
		
		ToDo todo = this.todos.get(position);
		tTitle.setText(todo.getTitle());
		tMessage.setText(todo.getMessage());
		tDate.setText(todo.getStringDate());
		tTime.setText(todo.getStringTime());
		
		return item;
	}
	private View expandTV(LinearLayout root, int[] depths) {
		for (int i = 0; i < depths.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depths[i]);
		}
		return  root.getChildAt(depths[depths.length - 1]);
	}

}
