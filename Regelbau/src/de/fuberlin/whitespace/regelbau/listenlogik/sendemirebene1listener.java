package de.fuberlin.whitespace.regelbau.listenlogik;

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

	public sendemirebene1listener(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Satzanzeige satzanzeige = (Satzanzeige)obereregelbuttonview;
		String[] tmp = {(String)((TextView)arg1).getText()};
		if(((String)((TextView)arg1).getText()).contains("SMS")){ 
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			listview.setAdapter(adapter);
			//listview.setOnItemClickListener(new zeigemirebene1listener(obereregelbuttonview, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("E-Mail")){ // else if wichtig, da sonst arg1 ge�ndert wurde.
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("WhatsApp")){
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
		else if(((String)((TextView)arg1).getText()).contains("Rauchzeichen")){
			String[] elemente = {};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
	}

}
