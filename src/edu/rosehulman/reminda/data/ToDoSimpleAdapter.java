package edu.rosehulman.reminda.data;

import java.util.ArrayList;

import edu.rosehulman.reminda.R;
import edu.rosehulman.reminda.entities.ToDo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToDoSimpleAdapter extends ToDoAdapter {

	public ToDoSimpleAdapter(ArrayList<ToDo> todos, Context c) {
		super(todos, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout item = (LinearLayout) inflater.inflate(R.layout.todo_list_item, null);
		int [] titleDepth = {0};
		int [] messageDepth = {1};
		int [] dateDepth = {2, 0};
		int [] timeDepth = {2, 1};
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
}
