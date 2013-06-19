package de.fuberlin.whitespace.regelbau.listenlogik;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.R.drawable;

public class ebene3listener_zeit implements OnItemClickListener, OnTimeSetListener {
	private View obereregelbuttonview;
	private ListView listview; 
	

	public ebene3listener_zeit(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Satzanzeige satzanzeige = (Satzanzeige) obereregelbuttonview;
		final Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
		if(((String)((TextView)arg1).getText()).contains("nach")){ // Fertig gedrückt.
		
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
			final Integer[] nums = new Integer[20];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = i*15;
			}
			 
    	//	satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
		/*	MyNumberPicker mynumberpicker = new MyNumberPicker(listview.getContext(), nums, new MyNumberPickerCallback() {
				
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
			*/
			
			final ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(listview.getContext(), android.R.layout.simple_list_item_1);
			adapter.addAll(nums);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					 int position = arg2;
					 Satzanzeige satz = ((Satzanzeige)obereregelbuttonview); 
					 satz.setButtonLabelDrei("nach "+adapter.getItem(position)+" Minuten");
					 listview.setAdapter(null);
					 satz.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					 satz.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					 satz.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				}
			
			} );
			 
			
			// TODO Zahleneingabe einbinden in neuen OnItemClickListener
		}else if(((String)((TextView)arg1).getText()).contains("um")){
			TimePickerDialog timepickerdialog = new TimePickerDialog(listview.getContext(),this,12,00,true);
			timepickerdialog.show();
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
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
