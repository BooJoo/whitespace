package de.fuberlin.whitespace.regelbau.listenlogik;

import android.R;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.R.array;
import de.fuberlin.whitespace.regelbau.R.drawable;
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
		String item = (String)((TextView)arg1).getText();
		ArrayAdapter<String> adapter;
		Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
		if(item.contains("Zeige mir")){ 
			elemente = satzanzeige.getResources().getStringArray(array.Button2WennZeigeMirArray);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			satzanzeige.setButtonLabelZwei("Raststätten");
			satzanzeige.setButtonLabelDrei("um 12:00 Uhr");
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			//adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1, elemente);
			listview.setAdapter(null);
//			listview.setAdapter(adapter);
//			listview.setOnItemClickListener(new ebene1listener_zeigemir(obereregelbuttonview, listview));
		}
		else if(item.contains("Erinnere mich")){ // else if wichtig, da sonst arg1 ge�ndert wurde.
			elemente = satzanzeige.getResources().getStringArray(array.Button2WennErinnereMichArray);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			satzanzeige.setButtonLabelZwei("Tanken");
			satzanzeige.setButtonLabelDrei("Tankstand unter 20% ist");

			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			//adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1, elemente);
			listview.setAdapter(null);
			//listview.setAdapter(adapter);
			//listview.setOnItemClickListener(new ebene1listener_erinneremich(obereregelbuttonview, listview));
		}
		else if(item.contains("Informiere mich")){
			elemente = satzanzeige.getResources().getStringArray(array.Button2WennInformiereMichArray);
			satzanzeige.setButtonLabelEins((String)((TextView)arg1).getText());
			satzanzeige.setButtonLabelZwei("Innentemperatur");
			satzanzeige.setButtonLabelDrei("> 20°C");
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			//adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(null);
//			listview.setAdapter(adapter);
//		    listview.setOnItemClickListener(new ebene1listener_informieremich(obereregelbuttonview, listview));
		}
		
		
		

	}

}
