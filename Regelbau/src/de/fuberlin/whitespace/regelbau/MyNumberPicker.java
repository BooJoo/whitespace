package de.fuberlin.whitespace.regelbau;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyNumberPicker <T> {
	 Context context;
	 T[] werte;
	 boolean eingabeBeendet;
	 String suffix;

	public MyNumberPicker (Context context, final ListView listview, final T[] werte, String suffix, final MyNumberPickerCallback<T> mynumberpickercallback, String alertName) {
		this.context = context;
		this.werte = werte;
		this.suffix = suffix;
		
		final ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, android.R.layout.simple_list_item_1);
		adapter.addAll(werte);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int position = arg2;
				 
				T wert = adapter.getItem(position);
				mynumberpickercallback.valueset(wert);
				
				listview.setAdapter(null);
			}
		
		} );

	}

	
	
}
