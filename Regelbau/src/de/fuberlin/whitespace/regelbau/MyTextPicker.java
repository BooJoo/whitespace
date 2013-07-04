package de.fuberlin.whitespace.regelbau;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class MyTextPicker {
	Context context;
    boolean eingabeBeendet;
    EditText input;
	
    public MyTextPicker(Context context, final MyPickerCallback<String> mynumberpickercallback, String alertName, String defaultValue) {
		this.context = context;
		
		input = new EditText(context);
		input.setText(defaultValue);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setView(input);
		alert.setTitle(alertName);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	       

			public void onClick(DialogInterface dialog, int whichButton) {
	          // Do something with value!
	        	 mynumberpickercallback.valueset(input.getText().toString());
	        	
	          }
	        });

	    
		alert.show();
		
	  	

	}

	
	
}