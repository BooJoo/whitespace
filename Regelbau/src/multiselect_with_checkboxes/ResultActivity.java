package multiselect_with_checkboxes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.id;
import de.fuberlin.whitespace.regelbau.R.layout;

public class ResultActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(layout.results);
		
		Bundle bundle = getIntent().getExtras();
		
		String[] results = bundle.getStringArray("selectedItems");
		
		ListView listView = (ListView)findViewById(id.outputList);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
		listView.setAdapter(adapter);
	}
}
