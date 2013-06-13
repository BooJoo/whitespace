package de.fuberlin.whitespace.regelbau.listenlogik;

import de.fuberlin.whitespace.regelbau.Satzanzeige;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class erinneremichebene1listener implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;

	public erinneremichebene1listener(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Satzanzeige satzanzeige = (Satzanzeige)obereregelbuttonview;
		String[] tmp = {(String)((TextView)arg1).getText()};
		if(((String)((TextView)arg1).getText()).contains("Sehen")){ 
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			listview.setAdapter(adapter);
			//listview.setOnItemClickListener(new zeigemirebene1listener(obereregelbuttonview, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("Pause")){ // else if wichtig, da sonst arg1 ge�ndert wurde.
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei("an eine "+tmp[0]);
			listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("Termin")){
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
	}

}
