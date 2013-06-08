package multiselect_with_checkboxes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R;

public class MultiSelectListViewActivity extends Activity implements OnClickListener{
	Button button;
	ListView listView;
	ArrayAdapter<String> adapter;
	public static String[] elemente;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiselect_listview);
		
		findViewById();
		
		String[] array = elemente;//getResources().getStringArray(R.array.test_array);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, array);
		
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter(adapter);
		
		button.setOnClickListener(this);
	}
	
	private void findViewById(){
		listView = (ListView)findViewById(R.id.list);
		button = (Button)findViewById(R.id.testbutton);
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onClick(View v) {
		SparseBooleanArray checked = listView.getCheckedItemPositions();
		ArrayList<String> selectedItems = new ArrayList<String>();
		
		for (int i = 0; i < checked.size(); i++) {
			int position = checked.keyAt(i);
			if(checked.valueAt(i))
				selectedItems.add(adapter.getItem(position));
		}
		
		String[] output = new String[selectedItems.size()];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = selectedItems.get(i);
		}
		Intent intent = new Intent();
		//Bundle Objekt
		Bundle bundle = new Bundle();
		bundle.putStringArray("selectedItems", output);
		
		//Bundle zu intent hinzufügen
		intent.putExtras(bundle);
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, intent);
		} else {
		    getParent().setResult(Activity.RESULT_OK, intent);
		}
		finish();
		
		/*
		Intent intent = new Intent(getApplicationContext(), RegelbauMain.class);
		
		//Bundle Objekt
		Bundle bundle = new Bundle();
		bundle.putStringArray("selectedItems", output);
		
		//Bundle zu intent hinzufügen
		intent.putExtras(bundle);
		
		//starte ResultActivity
		startActivity(intent);
		*/
	}

}
