package edu.rosehulman.reminda.data;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import edu.rosehulman.reminda.entities.ToDo;

public abstract class ToDoAdapter extends BaseAdapter {

	protected ArrayList<ToDo> todos;
	protected Context mContext;
	protected LayoutInflater inflater;

	public ToDoAdapter(ArrayList<ToDo> todos, Context c) {
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
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

	protected View expandTV(LinearLayout root, int[] depths) {
		for (int i = 0; i < depths.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depths[i]);
		}
		return root.getChildAt(depths[depths.length - 1]);
	}

}
