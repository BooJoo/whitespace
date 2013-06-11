package de.fuberlin.whitespace.regelbau;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;

public class MyNumberPicker {
	Context context;
	String[] werte;
     boolean eingabeBeendet;
     final NumberPicker np;
	public MyNumberPicker(Context context, final String[] werte, final MyNumberPickerCallback mynumberpickercallback, String alertName) {
		this.context = context;
		this.werte = werte;
		np = new NumberPicker(context);
		
		
		np.setMinValue(0);
		np.setMaxValue(werte.length-1);
		np.setDisplayedValues(werte);
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setView(np);
		alert.setTitle(alertName);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	       

			public void onClick(DialogInterface dialog, int whichButton) {
	          // Do something with value!
	        	 mynumberpickercallback.valueset( Integer.valueOf((werte[np.getValue()])));
	        	
	          }
	        });

	    
		alert.show();

	}

	
	
}
