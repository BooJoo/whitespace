package de.fuberlin.whitespace.regelbau.listenlogik;

import de.fuberlin.whitespace.regelbau.MyNumberPicker;
import de.fuberlin.whitespace.regelbau.MyNumberPickerCallback;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class sendemirebene1listener implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;

	public sendemirebene1listener(View obereregelbuttonview, ListView listview) {
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Satzanzeige satzanzeige = (Satzanzeige) obereregelbuttonview;
		String[] tmp = { (String) ((TextView) arg1).getText() };
		if (((String) ((TextView) arg1).getText()).contains("Außentemperatur")) {
			final String[] nums = new String[6];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 5);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							//satz.setButtonLabelEins("Informiere mich");
							satz.setButtonLabelZwei("Außentemperatur");
							satz.setButtonLabelDrei("über " + value + " °C");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
					},"Setze Außentemperatur über...");
			// listview.setOnItemClickListener(new
			// zeigemirebene1listener(obereregelbuttonview, listview));
		} else if (((String) ((TextView) arg1).getText())
				.contains("Durchschnittsgeschwindigkeit")) { // else if wichtig,
																// da sonst arg1
																// ge�ndert
																// wurde.
			final String[] nums = new String[10];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 20);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							//satz.setButtonLabelEins("Informiere mich");
							satz.setButtonLabelZwei("Durchschnittsgeschwindigkeit");
							satz.setButtonLabelDrei("über " + value + " Km/h");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
					},"Setze Durchschnittsgeschw. über...");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null,
			// listview));
		} else if (((String) ((TextView) arg1).getText()).contains("Tankstand")) {
			final String[] nums = new String[11];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 10);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							//satz.setButtonLabelEins("Informiere mich über");
							//satz.setButtonLabelZwei("Tankstellen, ");
							satz.setButtonLabelDrei("Tankstand unter " + value + "% ist.");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
						
					},"Setze Tankstand unter...");
			// listview.setOnItemClickListener(new zeigemirebene1listener(null,
			// listview));
		} else if (((String) ((TextView) arg1).getText()).contains("Innentemperatur")) {
			final String[] nums = new String[6];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 5);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							satz.setButtonLabelEins("Informiere mich, wenn");
							satz.setButtonLabelZwei("die Innentemperatur");
							satz.setButtonLabelDrei("auf über " + value + " °C steigt.");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
						
					},"Setze Innentemperatur über...");
		} else if (((String) ((TextView) arg1).getText()).contains("Lautstärke")) {
			final String[] nums = new String[21];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 5);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							//satz.setButtonLabelEins("Informiere mich");
							satz.setButtonLabelZwei("Lautstärke");
							satz.setButtonLabelDrei("über " + value + " %");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
						
					},"Setze Lautstärke über...");
		} else if (((String) ((TextView) arg1).getText()).contains("Regensensor")) {
			final String[] nums = new String[41];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 5);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							//satz.setButtonLabelEins("Informiere mich");
							satz.setButtonLabelZwei("Regensensor");
							satz.setButtonLabelDrei("über " + value + " %");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
						
					},"Setze Luftfeuchtigkeit über...");
		} else if (((String) ((TextView) arg1).getText()).contains("Tankstelle")) {
			final String[] nums = new String[11];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = Integer.toString(i * 10);
			}

			// satz.setButtonLabelDrei("nach "+nums[np.getValue()]+" Minuten");
			MyNumberPicker mynumberpicker = new MyNumberPicker(
					listview.getContext(), nums, new MyNumberPickerCallback() {

						@Override
						public void valueset(int value) {
							Satzanzeige satz = ((Satzanzeige) obereregelbuttonview);
							//satz.setButtonLabelEins("Informiere mich über");
							//satz.setButtonLabelZwei("Tankstellen, ");
							satz.setButtonLabelDrei("wenn mein Tankstand unter " + value + "% ist.");

						}

						@Override
						public void valueset(String value) {
							// TODO Auto-generated method stub
							
						}
						
					},"Setze Tankstand unter...");
		} 
		// else
		// if(((String)((TextView)arg1).getText()).contains("Rauchzeichen")){
		// String[] elemente = {};
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
		// satzanzeige.setButtonLabelZwei(tmp);
		// listview.setAdapter(adapter);
		// // listview.setOnItemClickListener(new zeigemirebene1listener(null,
		// listview));
		// }
	}

}
