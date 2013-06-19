package de.fuberlin.whitespace.regelbau.listenlogik;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.MySlotmachine;
import de.fuberlin.whitespace.regelbau.MySlotmachineCallback;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.Satzanzeige;

public class ebene2listener_informieremich implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;
	Satzanzeige satzanzeige; 
	
	public ebene2listener_informieremich(View obereregelbuttonview, ListView listview) {
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		satzanzeige = (Satzanzeige) obereregelbuttonview;
		final String item = (String) ((TextView) arg1).getText();
		final Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
		final String[] operator = listview.getResources().getStringArray(R.array.Operator);
		final String[] einheit = listview.getResources().getStringArray(R.array.Einheit);
		final String op = "Operator";
		final String unit = "Einheit";
		final String value = "Wert";
		
		if (item.contains("Außentemperatur")) {
			
			listview.setAdapter(null);
			final String[] nums = new String[6];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
			new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
				
				@Override
				public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
					satzanzeige.setButtonLabelDrei("wenn die "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3);
					satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				}
			});
		} else if (item.contains("Durchschnittsgeschwindigkeit")){
			listview.setAdapter(null);
			final String[] nums = new String[10];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 20);
			new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
				
				@Override
				public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
					satzanzeige.setButtonLabelDrei("wenn die "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3);
					satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				}
			});
			
		} else if (item.contains("Tankstand")) {
		 
			listview.setAdapter(null);
			final String[] nums = new String[11];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 10);
			new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
				
				@Override
				public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
					satzanzeige.setButtonLabelDrei("wenn der "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3);
					satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				}
			});
			
		} else if (item.contains("Innentemperatur")) {
			listview.setAdapter(null);
			final String[] nums = new String[6];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
			new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
				
				@Override
				public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
					satzanzeige.setButtonLabelDrei("wenn die "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3);
					satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				}
			});
			
			} else if (item.contains("Lautstärke")) {
				listview.setAdapter(null);
				final String[] nums = new String[21];
				for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
				new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
					
					@Override
					public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
						satzanzeige.setButtonLabelDrei("wenn die "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3);
						satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
						satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
						satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					}
				});
				
		} else if (item.contains("Regensensor")) {
			 
			
			listview.setAdapter(null);
			final String[] nums = new String[41];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
			new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
				
				@Override
				public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
					satzanzeige.setButtonLabelDrei("wenn der "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3);
					satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				}
			});
			
		} else if (item.contains("Tankstelle")) {
		 
			listview.setAdapter(null);
			final String[] nums = new String[11];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 10);
			
			new MySlotmachine(listview.getContext(), listview, op, operator, value, nums, unit, einheit, new MySlotmachineCallback() {
				
				@Override
				public void values(String wertSpalte1, String wertSpalte2, String wertSpalte3) {
					satzanzeige.setButtonLabelDrei("wenn eine "+item+" "+wertSpalte1+" "+wertSpalte2+" "+wertSpalte3+" entfernt.");
					satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
					
				}
			});
			
		} 
		
	}

}
