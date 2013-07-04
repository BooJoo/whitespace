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
	Number[] nums;
	String[] ops;
	String[] units;
	
	final MyNumberPicker<Number> values;
	final MyTextPicker operator;
	final MyTextPicker unit;
	
	public MyMergedPicker(Context context, final MyPickerCallback<String> mynumberpickercallback, String alertName){
		this.context = context;
		input = new EditText(context);
		
		nums = new Number[20];
		ops = new String[3];
		units = new String[10];
		
		final String defaultValue = "100";
		final String defaultUnit = "km";
		final String defaultOperator = "<";
		
		for (int i = 0; i < nums.length; i++) nums[i] = i*15;
		ops = listview.getResources().getStringArray(R.array.Operator);
		//units = listview.getResources().getStringArray(R.array.Einheit);
		
		//Operator Picker
		operator = new MyTextPicker(listview.getContext(), new MyPickerCallback<String>(){
		    	
			@Override
			public void valueset(String value) {}
			
		}, "Operator",
		"");
		
		
		//Einheiten Picker
		unit = new MyTextPicker(listview.getContext(), new MyPickerCallback<String>(){

			@Override
			public void valueset(String value) {}
			
		}, "Einheit",
		"");

		
		//Zahlen Picker
		values = new MyNumberPicker<Number>(listview.getContext(),listview, nums, "", new MyPickerCallback<Number>() {
		
			@Override
			public void valueset(Number value) {
				Satzanzeige satz = ((Satzanzeige)obereregelbuttonview); 
				//satz.setButtonLabelDrei(defaultOperator+" " +defaultValue+" "+defaultUnit);
				
			}
			
		},"Zahlenwert");

	}

}
