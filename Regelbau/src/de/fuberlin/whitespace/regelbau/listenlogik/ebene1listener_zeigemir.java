package de.fuberlin.whitespace.regelbau.listenlogik;

import java.util.Vector;

import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.R.drawable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ebene1listener_zeigemir implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;
	private Satzanzeige satzanzeige;
	
	public ebene1listener_zeigemir(View obereregelbuttonview, final ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
		
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //Multichoice einstellen
		//Hinzufgüen von FERTIG am Ende der Liste.
		Adapter adapter = listview.getAdapter();
		String[] copyarray = new String[adapter.getCount()];
		for(int i = 0; i < adapter.getCount(); i++) 
			copyarray[i] = (String)adapter.getItem(i);
		//TODO "F E R T I G" weg und Button dahin
	//	copyarray[copyarray.length-1] = "F E R T I G";
		ArrayAdapter<String> adapterneu = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_multiple_choice,copyarray);
				/*{
			@Override
	        public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater mInflater = (LayoutInflater)(listview.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
				
				return parent;
				
			}
		};*/
		
		
		listview.setAdapter(adapterneu);
		// OK fertig hinzugefügt.
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
	//	if(((String)((TextView)arg1).getText()).contains("F E R T I G")){ // Fertig gedrückt.
			Vector<String> checkedItems = new Vector<String>();
			SparseBooleanArray checked = listview.getCheckedItemPositions();
			
			for (int i = 0; i < listview.getCount(); i++){
				if (checked.get(i)) {
					checkedItems.add((String) listview.getAdapter().getItem(i));
				}
			}	
			
			/** auskommentiert, da Baukasten leer sein soll, wenn auf Button geklickt wurde */
		
			satzanzeige = (Satzanzeige)obereregelbuttonview;
			satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			Object tmpElements[] = checkedItems.toArray();
			//satzanzeige.setButtonLabelZwei(tmpElements);
			String buttonLabel = "";
			
			if(checkedItems.size() == 0) 
				satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			if(checkedItems.size() == 2){
				buttonLabel += checkedItems.get(0)+" und "+checkedItems.get(1);
			}
			else if(checkedItems.size() > 2){ 
				for (int i = 0; i < checkedItems.size(); i++) 
					if(checkedItems.size()-1 == i)
						buttonLabel += (tmpElements[i]+"");
					else
						buttonLabel += (tmpElements[i]+", ");
			}
			else{
				if(checkedItems.size() == 1) 
					buttonLabel += tmpElements[0];
			}
			
			satzanzeige.setButtonLabelZwei(buttonLabel);

	}

}
