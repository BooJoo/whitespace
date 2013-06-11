package de.fuberlin.whitespace.regelbau.listenlogik;

import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import android.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Erste Menuebene
 * @author Christoph
 *
 */
public class ebene0listener implements OnItemClickListener {


	private View obereregelbuttonview;
	private ListView listview;
	public ebene0listener(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println((String)((TextView)arg1).getText());
		Satzanzeige satzanzeige = (Satzanzeige)obereregelbuttonview;
		if(((String)((TextView)arg1).getText()).contains("Zeige mir")){ 
			String[] elemente = {"Raststätten","Tankstellen","McDonalds","Subway"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			String[] defaults = {"Raststätten","um 12:00 Uhr"};
			satzanzeige.setButtonLabelZwei(defaults[0]);
			satzanzeige.setButtonLabelDrei(defaults[1]);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new zeigemirebene1listener(obereregelbuttonview, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("Erinnere mich")){ // else if wichtig, da sonst arg1 ge�ndert wurde.
			String[] elemente = {"Sehenswürdigkeiten","Pause","Termin"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			String[] defaults = {"Tankstellen","Tankstand < 20%"};
			satzanzeige.setButtonLabelZwei(defaults[0]);
			satzanzeige.setButtonLabelDrei(defaults[1]);
			listview.setOnItemClickListener(new erinneremichebene1listener(satzanzeige, listview));
			listview.setAdapter(adapter);
		}
		else if(((String)((TextView)arg1).getText()).contains("Informiere mich")){
			String[] elemente = {"Außentemperatur","Innentemperatur","Durchschnittsgeschwindigkeit","Lautstärke (Radio)", "Regensensor", "Tankstand"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			String[] defaults = {"Innentemperatur","> 20°C"};
			satzanzeige.setButtonLabelZwei(defaults[0]);
			satzanzeige.setButtonLabelDrei(defaults[1]);			
			listview.setAdapter(adapter);
		    listview.setOnItemClickListener(new sendemirebene1listener(satzanzeige, listview));
		}
		else{
			String[] elemente = listview.getContext().getResources().getStringArray(de.fuberlin.whitespace.regelbau.R.array.ListDummy);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
		}

	}

}
