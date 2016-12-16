package kishore.com.multiselectwithdelete;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AbsListView.MultiChoiceModeListener;

import java.util.ArrayList;
import java.util.List;

import kishore.com.multiselectwithdelete.Adapters.ListViewAdapter;

public class MainActivity extends Activity {

    // Declare Variables
    private ListView listView;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        List<String> itemList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            itemList.add("Item " + i);
        }

        // Here you have to pass R.layout.list_view_item as a constructor parameter of LisTViewAdapter since ListViewAdapter is an ArrayAdapter which itself internally doesn't have default constructor and it has 3parameter constructor
        listViewAdapter = new ListViewAdapter(this, R.layout.list_view_item, itemList);

        // Binds the your Adapter to the ListView
        listView.setAdapter(listViewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        // Capture ListView item click
        listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                listViewAdapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Fetching all selected Id's from ListViewAdapter Class
                        SparseBooleanArray selected = listViewAdapter.getSelectedIds();

                        // Here we capture all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                String selectedItem = listViewAdapter.getItem(selected.keyAt(i));

                                // Remove selected items following the ids
                                listViewAdapter.remove(selectedItem);
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                listViewAdapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }
}
