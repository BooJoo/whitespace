package de.fuberlin.whitespace.regelbau.listenlogik;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ebene2und3listener_auto implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;

	public ebene2und3listener_auto(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(((String)((TextView)arg1).getText()).contains("Zeit")){ // Fertig gedrückt.
			String[] elemente = {"nach ... Minuten","um ... Uhr"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene3listener_zeit(obereregelbuttonview, listview));
			// TODO Zahleneingabe einbinden in neuen OnItemClickListener
		}else if(((String)((TextView)arg1).getText()).contains("Ort")){
			String[] elemente = {"nach ... Kilometern", "in ... (Ort)","auf der Strecke nach ..."};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene3listener_ortstrecke(obereregelbuttonview, listview));
			// Hier auch
		}	
		else if(((String)((TextView)arg1).getText()).contains("Auto")){
			String[] elemente = {"Außentemperatur","Innentemperatur","Durchschnittsgeschwindigkeit",
					"Lautstärke (Radio)","Regensensor","Tankstand"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setOnItemClickListener(new ebene1listener_informieremich(obereregelbuttonview,listview));
			listview.setAdapter(adapter);
			//
		}
		System.out.println();
	}

}
