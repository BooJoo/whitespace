package de.fuberlin.whitespace.regelbau;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MySlotmachineOnItemClickListener implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;
	private MyNumberPickerCallback mynumberpickercallback;

	public MySlotmachineOnItemClickListener(View obereregelbuttonview, ListView listview, MyNumberPickerCallback mynumberpickercallback){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
		this.mynumberpickercallback = mynumberpickercallback;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String wert = listview.getAdapter().getItem(arg2).toString();
		mynumberpickercallback.valueset(wert);
		
	}

}
