package de.fuberlin.whitespace.regelbau.listenlogik;

import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.R.drawable;
import android.graphics.drawable.Drawable;
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
		Satzanzeige satzanzeige = (Satzanzeige) obereregelbuttonview;
		Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
		String item = (String)((TextView)arg1).getText();
		if(item.contains("Zeit")){ // Fertig gedrückt.
			String[] elemente = listview.getResources().getStringArray(R.array.Button3WennZeit);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene3listener_zeit(obereregelbuttonview, listview));
			// TODO Zahleneingabe einbinden in neuen OnItemClickListener
		}else if(item.contains("Ort")){
			String[] elemente = listview.getResources().getStringArray(R.array.Button3WennOrtStrecke);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene3listener_ortstrecke(obereregelbuttonview, listview));
			
		}	
		else if(item.contains("Auto")){
			String[] elemente = listview.getResources().getStringArray(R.array.Button3WennAuto);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setOnItemClickListener(new ebene2listener_informieremich(obereregelbuttonview,listview));
			listview.setAdapter(adapter);
		}
		//System.out.println();
	}

}
