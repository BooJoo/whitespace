package de.fuberlin.whitespace.regelbau;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyNumberPicker {
	 Context context;
	 String[] werte;
     boolean eingabeBeendet;
     //final NumberPicker np;

	public MyNumberPicker(Context context, final ListView listview, final String[] werte, final MyNumberPickerCallback mynumberpickercallback, String alertName) {
		this.context = context;
		this.werte = werte;
		/*np = new NumberPicker(context);
		
		
		np.setMinValue(0);
		np.setMaxValue(werte.length-1);
		np.setDisplayedValues(werte);
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setView(np);
		alert.setTitle(alertName);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	       

			public void onClick(DialogInterface dialog, int whichButton) {
	          // Do something with value!
	        	 mynumberpickercallback.valueset(Integer.valueOf((werte[np.getValue()])));
	        	
	          }
	        });

	    
		alert.show();*/
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
		adapter.addAll(werte);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int position = arg2;
				 
				String wert = adapter.getItem(position);
				mynumberpickercallback.valueset(wert);
				mynumberpickercallback.valueset(Integer.valueOf(wert));
				
				listview.setAdapter(null);
			}
		
		} );

	}

	
	
}
