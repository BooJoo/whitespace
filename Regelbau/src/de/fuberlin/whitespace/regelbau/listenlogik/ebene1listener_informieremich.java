package de.fuberlin.whitespace.regelbau.listenlogik;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.MySlotmachine;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.Satzanzeige;

public class ebene1listener_informieremich implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;
	Satzanzeige satzanzeige; 
	
	public ebene1listener_informieremich(View obereregelbuttonview, ListView listview) {
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		satzanzeige = (Satzanzeige) obereregelbuttonview;
		String item = (String) ((TextView) arg1).getText();
		satzanzeige.setButtonLabelZwei("über die "+item);
		
		final String[] operator = listview.getResources().getStringArray(R.array.Operator);
		final String[] einheit = listview.getResources().getStringArray(R.array.Einheit);
		
		if (item.contains("Außentemperatur")) {
	
			final String[] nums = new String[6];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
			
			new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);
			//satzanzeige.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");		
			//listview.setOnItemClickListener(new zeigemirebene1listener(obereregelbuttonview, listview));
		
		} else if (item.contains("Durchschnittsgeschwindigkeit")){
			
			final String[] nums = new String[10];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 20);
			
			new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
			
		} else if (item.contains("Tankstand")) {
			satzanzeige.setButtonLabelZwei("über den "+item);
			final String[] nums = new String[11];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 10);
			new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);

			
			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
			
		} else if (item.contains("Innentemperatur")) {
			final String[] nums = new String[6];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
			
			new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);
			
			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
			
			} else if (item.contains("Lautstärke")) {
				final String[] nums = new String[21];
				for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
		
				new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);
				
				// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
				// listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
				
		} else if (item.contains("Regensensor")) {
			satzanzeige.setButtonLabelZwei("über den "+item);
			final String[] nums = new String[41];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 5);
			new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);
			
			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
			
		} else if (item.contains("Tankstelle")) {
			satzanzeige.setButtonLabelZwei("über "+item);
			final String[] nums = new String[11];
			for (int i = 0; i < nums.length; i++) nums[i] = Integer.toString(i * 10);
			new MySlotmachine(listview.getContext(), listview, "Operator", operator, "Werte", nums, "Einheit", einheit);
			
			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
			
		} 
		
	}

}
