package de.fuberlin.whitespace.regelbau.listenlogik;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.MyNumberPicker;
import de.fuberlin.whitespace.regelbau.MyNumberPickerCallback;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.R.drawable;

public class ebene3listener_ortstrecke implements OnItemClickListener{
	

	private View obereregelbuttonview;
	private ListView listview;

	public ebene3listener_ortstrecke(View obereregelbuttonview, ListView listview) {
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Satzanzeige satzanzeige = (Satzanzeige) obereregelbuttonview;
		final Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
		if (((String) ((TextView) arg1).getText()).contains("nach ...")) {
			final String[] nums = new String[10];
			for (int i = 0; i < nums.length; i++) 
				nums[i] = Integer.toString(i * 15);
			

			new MyNumberPicker(
				listview.getContext(), listview,nums, new MyNumberPickerCallback() {
	
				@Override
				public void valueset(int value) {
					Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
					satz.setButtonLabelDrei("wenn ich " + value + " km gefahren bin.");
					satz.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satz.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satz.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				
					
				}

				@Override
				public void valueset(String value) {
					}
			}, "gefahrene Kilometer ...");
			
			} 
		
	}

}


