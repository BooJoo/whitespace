package de.fuberlin.whitespace.regelbau.listenlogik;

import de.fuberlin.whitespace.regelbau.MyNumberPicker;
import de.fuberlin.whitespace.regelbau.MyNumberPickerCallback;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TimePicker;

public class zeitebene3listener implements OnItemClickListener, OnTimeSetListener {
	private View obereregelbuttonview;
	private ListView listview; 
	

	public zeitebene3listener(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(((String)((TextView)arg1).getText()).contains("Nach")){ // Fertig gedrückt.
		/*	final NumberPicker np = new NumberPicker(listview.getContext());
			
			final String[] nums = new String[20];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i*15);
			}
			np.setMinValue(0);
			np.setMaxValue(nums.length-1);
			np.setDisplayedValues(nums);
			AlertDialog.Builder alert = new AlertDialog.Builder(listview.getContext());
			alert.setView(np);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		          // Do something with value!
		        	Satzanzeige satz = ((Satzanzeige)obereregelbuttonview);  
		    		satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
		        	
		          }
		        });

		    
			alert.show();
			*/
			final String[] nums = new String[20];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i*15);
			}
			 
    	//	satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(listview.getContext(), nums, new MyNumberPickerCallback() {
				
				@Override
				public void valueset(int value) {
					Satzanzeige satz = ((Satzanzeige)obereregelbuttonview); 
					satz.setButtonLabelDrei("nach "+value+" Minuten");
					
				}

				@Override
				public void valueset(String value) {
					// TODO Auto-generated method stub
					
				}
			},"Setze nach ... Minuten");
			
			 
			
			// TODO Zahleneingabe einbinden in neuen OnItemClickListener
		}else if(((String)((TextView)arg1).getText()).contains("Um")){
			TimePickerDialog timepickerdialog = new TimePickerDialog(listview.getContext(),this,12,00,true);
			timepickerdialog.show();
			// Hier auch
		}	
		
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		System.out.println(hourOfDay+" "+minute);
		Satzanzeige satz = ((Satzanzeige)obereregelbuttonview);  
		satz.setButtonLabelDrei("um "+hourOfDay+":"+String.format("%02d", minute)+" Uhr");
	}

}
