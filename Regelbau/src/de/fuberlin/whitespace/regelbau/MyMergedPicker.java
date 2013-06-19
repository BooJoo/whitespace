package de.fuberlin.whitespace.regelbau;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class MyMergedPicker {
	Context context;
	EditText input;
	View obereregelbuttonview;
	ListView listview; 
	String[] nums;
	String[] ops;
	String[] units;
	
	final MyNumberPicker values;
	final MyTextPicker operator;
	final MyTextPicker unit;
	
	public MyMergedPicker(Context context, final MyNumberPickerCallback mynumberpickercallback, String alertName){
		this.context = context;
		input = new EditText(context);
		
		nums = new String[20];
		ops = new String[3];
		units = new String[10];
		
		final String defaultValue = "100";
		final String defaultUnit = "km";
		final String defaultOperator = "<";
		
		for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i*15);
		ops = listview.getResources().getStringArray(R.array.Operator);
		units = listview.getResources().getStringArray(R.array.Einheit);
		
		//Operator Picker
		operator = new MyTextPicker(listview.getContext(), new MyNumberPickerCallback(){

			@Override
			public void valueset(int value) {}

			@Override
			public void valueset(String value) {}
			
		}, "Operator");
		
		
		//Einheiten Picker
		unit = new MyTextPicker(listview.getContext(), new MyNumberPickerCallback(){

			@Override
			public void valueset(int value) {}

			@Override
			public void valueset(String value) {}
			
		}, "Einheit");

		
		//Zahlen Picker
		values = new MyNumberPicker(listview.getContext(),listview, nums, new MyNumberPickerCallback() {
		
			@Override
			public void valueset(int value) {
				Satzanzeige satz = ((Satzanzeige)obereregelbuttonview); 
				satz.setButtonLabelDrei(defaultOperator+" " +defaultValue+" "+defaultUnit);
				
			}

			@Override
			public void valueset(String value) {}
			
		},"Zahlenwert");

	}

}
