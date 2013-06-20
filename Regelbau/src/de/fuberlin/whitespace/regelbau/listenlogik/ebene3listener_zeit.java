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
		
			
			final Integer[] nums = new Integer[20];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = i*15;
			}
			 
    	
			
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
			 
			
		}else if(((String)((TextView)arg1).getText()).contains("um")){
			TimePickerDialog timepickerdialog = new TimePickerDialog(listview.getContext(),this,12,00,true);
			timepickerdialog.show();
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			
		}	
		
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		System.out.println(hourOfDay+" "+minute);
		Satzanzeige satz = ((Satzanzeige)obereregelbuttonview);  
		satz.setButtonLabelDrei("um "+hourOfDay+":"+String.format("%02d", minute)+" Uhr");
	}

}
