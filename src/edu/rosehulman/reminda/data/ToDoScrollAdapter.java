package edu.rosehulman.reminda.data;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.rosehulman.reminda.R;
import edu.rosehulman.reminda.entities.ToDo;

public class ToDoScrollAdapter extends ToDoAdapter {

	public ToDoScrollAdapter(ArrayList<ToDo> todos, Context c) {
		super(todos, c);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout item = (LinearLayout) inflater.inflate(R.layout.todo_scroll_item, null);
		int [] titleDepth = {0};
		TextView tTitle = (TextView) expandTV(item, titleDepth);
		ToDo todo = this.todos.get(position);
		tTitle.setText(todo.getTitle());	
		return item;
	}

}
