package de.fuberlin.whitespace.regelbau.listenlogik;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.R.array;
import de.fuberlin.whitespace.regelbau.Satzanzeige;

/**
 * Erste Menuebene
 * @author Christoph
 *
 */
public class ebene0listener implements OnItemClickListener {


	private View obereregelbuttonview;
	private ListView listview;
	Satzanzeige satzanzeige;
	
	public ebene0listener(View obereregelbuttonview, ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		satzanzeige = (Satzanzeige)obereregelbuttonview;
		String[] elemente = new String[Integer.SIZE];
		ArrayAdapter<String> adapter;
		if(((String)((TextView)arg1).getText()).contains("Zeige mir")){ 
			elemente = satzanzeige.getResources().getStringArray(array.Button2WennZeigeMirArray);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			satzanzeige.setButtonLabelZwei("Raststätten");
			satzanzeige.setButtonLabelDrei("um 12:00 Uhr");
			
			//adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1, elemente);
			listview.setAdapter(null);
//			listview.setAdapter(adapter);
//			listview.setOnItemClickListener(new ebene1listener_zeigemir(obereregelbuttonview, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("Erinnere mich")){ // else if wichtig, da sonst arg1 ge�ndert wurde.
			elemente = satzanzeige.getResources().getStringArray(array.Button2WennErinnereMichArray);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			satzanzeige.setButtonLabelZwei("Tanken");
			satzanzeige.setButtonLabelDrei("Tankstand unter 20% ist");
			
			//adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1, elemente);
			listview.setAdapter(null);
			//listview.setAdapter(adapter);
			//listview.setOnItemClickListener(new ebene1listener_erinneremich(obereregelbuttonview, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("Informiere mich")){
			elemente = satzanzeige.getResources().getStringArray(array.Button2WennInformiereMichArray);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			satzanzeige.setButtonLabelZwei("Innentemperatur");
			satzanzeige.setButtonLabelDrei("> 20°C");
			
			//adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(null);
//			listview.setAdapter(adapter);
//		    listview.setOnItemClickListener(new ebene1listener_informieremich(obereregelbuttonview, listview));
		}
		
		
		

	}

}
