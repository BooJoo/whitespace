package de.fuberlin.whitespace.regelbau.listenlogik;

import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.R.drawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ebene1listener_erinneremich implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;
	Satzanzeige satzanzeige;

	public ebene1listener_erinneremich(View obereregelbuttonview,ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;	
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		satzanzeige = (Satzanzeige)obereregelbuttonview;
		Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
		String[] tmp = {(String)((TextView)arg1).getText()};
		String[] elemente = new String[Integer.SIZE];
		String item = (String)((TextView)arg1).getText();
		if(item.contains("Sehenswürdigkeiten")){ 
			//elemente = "";
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei("an "+tmp);
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene1listener_zeigemir(obereregelbuttonview, listview));
		}
		else if(item.contains("Pause")){ // else if wichtig, da sonst arg1 ge�ndert wurde.
			//String[] elemente = {};
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei("an eine "+tmp[0]);
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			listview.setAdapter(null);
			//listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
		else if(item.contains("Termin")){
			//String[] elemente = {};
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			listview.setAdapter(null);
			//listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
		else if(item.contains("Tank")){
			//String[] elemente = {};
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			satzanzeige.setButtonLabelZwei(tmp);
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			listview.setAdapter(null);
			//listview.setAdapter(adapter);
		//	listview.setOnItemClickListener(new zeigemirebene1listener(null, listview));
		}
		else if(item.contains("Regenschirm")){
			satzanzeige.setButtonLabelZwei(item);
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			listview.setAdapter(null);
		}
	}

}
