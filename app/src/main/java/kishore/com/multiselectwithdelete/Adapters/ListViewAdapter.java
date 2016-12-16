package kishore.com.multiselectwithdelete.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kishore.com.multiselectwithdelete.R;

public class ListViewAdapter extends ArrayAdapter<String> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<String> itemList;
	private SparseBooleanArray selectedItemsIds;

	public ListViewAdapter(Context context, int resourceId,
						   List<String> itemList) {
		super(context, resourceId, itemList);
		selectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.itemList = itemList;
		inflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView item;
		/* Here you can can add extra if you want */
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_view_item, null);
			holder.item = (TextView) view.findViewById(R.id.item);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Capture position and set to the TextViews
		holder.item.setText(itemList.get(position));
		return view;
	}

	@Override
	public void remove(String item) {
		itemList.remove(item);
		notifyDataSetChanged();
	}

	public List<String> getString() {
		return itemList;
	}

	public void toggleSelection(int position) {
		selectView(position, !selectedItemsIds.get(position));
	}

	public void removeSelection() {
		selectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value) {
			selectedItemsIds.put(position, value);
		} else {
			selectedItemsIds.delete(position);
		}
			
		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return selectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return selectedItemsIds;
	}
}