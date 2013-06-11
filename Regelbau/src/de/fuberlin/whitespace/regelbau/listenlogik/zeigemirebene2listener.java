package de.fuberlin.whitespace.regelbau.listenlogik;

import java.util.Vector;

import de.fuberlin.whitespace.regelbau.Satzanzeige;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class zeigemirebene2listener implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;

	public zeigemirebene2listener(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(((String)((TextView)arg1).getText()).contains("Zeit")){ // Fertig gedrückt.
			String[] elemente = {"Nach ... Minuten","Um ... Uhr"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new zeitebene3listener(obereregelbuttonview, listview));
			// TODO Zahleneingabe einbinden in neuen OnItemClickListener
		}else if(((String)((TextView)arg1).getText()).contains("Ort")){
			String[] elemente = {"Nach ... Kilometern", "In ... (Ort)","Auf der Strecke nach ..."};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			// Hier auch
		}	
		else if(((String)((TextView)arg1).getText()).contains("Auto")){
			String[] elemente = {"Außentemperatur","Innentemperatur","Durchschnittsgeschwindigkeit",
					"Lautstärke (Radio)","Regensensor","Tankstand"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setOnItemClickListener(new sendemirebene1listener(obereregelbuttonview,listview));
			listview.setAdapter(adapter);
			//
		}
		System.out.println();
	}

}
