package de.fuberlin.whitespace.regelbau;

import de.fuberlin.whitespace.regelbau.listenlogik.ebene0listener;
import de.fuberlin.whitespace.regelbau.listenlogik.erinneremichebene1listener;
import de.fuberlin.whitespace.regelbau.listenlogik.sendemirebene1listener;
import de.fuberlin.whitespace.regelbau.listenlogik.zeigemirebene1listener;
import de.fuberlin.whitespace.regelbau.listenlogik.zeigemirebene2listener;
import de.fuberlin.whitespace.regelbau.listenlogik.zeigemirebene2listener;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Klasse zum Anzeigen der unterschiedlichen Optionen des Regelbaus
 * @author Christoph, Maria
 *
 */
public class Optionsanzeige extends LinearLayout implements IButtonChangeListener{

	private String[] options;
	ListView listview;
	OptionsanzeigeListAdapter rola;
	Satzanzeige satzanzeige;
    Main rbm;
	
	public Optionsanzeige(Context context, String[] options, Satzanzeige rbs) {
		super(context);
		rbm = (Main) context;
		this.options = options;
		listview = new ListView(context);
		//ColorDrawable sage= new ColorDrawable(this.getResources().getColor(Color.RED));
		//lv.setDivider(this.getResources().getDrawable(android.R.color.background_dark));
		//int[] colors = {0, 0xFFFF0000, 0}; // red for the example
		//lv.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		//lv.setDividerHeight(1);
		//lv.setBackgroundColor(Color.BLACK);
		listview.setLayoutParams(new GridView.LayoutParams(-1,-2));
		//rola = new OptionsanzeigeListAdapter(getContext(),options,rbm);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,context.getResources().getStringArray(de.fuberlin.whitespace.regelbau.R.array.ListDummy));
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ebene0listener(rbs, listview));
		addView(listview);
		this.satzanzeige = rbs;
		rbs.registerForButtonChanges(this);
	}
	
	public void setNewElements(String[] ele){
		rola.setNewElements(ele);
	}
	
	
	
	public void init(){
		for(int i = 0; i < options.length; i++){
			// Hier werden die Listenelemente angezeigt
			System.out.println(""+options[i]);
		}
	}

	/*
	 * Wird aufgerufen, wenn Button 3 gedrückt wurde.
	 * (non-Javadoc)
	 * @see de.fuberlin.whitespace.regelbau.IButtonChangeListener#ausloeserchanged(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void ausloeserchanged(String oldbuttontext,String textVonButton1, String textVonButton2) {
		//String[] tmp = {"Zeige mir...","Erinnere mich...","Sende..."};
		//setNewElements(tmp);
		/*if(aktion.compareTo("Zeige mir") == 0){
			String[] tmp = {"Bestimmter Zeitpunkt","Bestimmte Ortsangabe","Bestimmter Zustand"};
			setNewElements(tmp);
		}else{
			String[] tmp = {"Wird","noch","nicht","unterstützt"};
			setNewElements(tmp);
		}*/
			String[] tmp = {"Zeit","Ort/Strecke","Auto"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,tmp);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new zeigemirebene2listener(satzanzeige, listview));
	
		
	}

	/*
	 * Wird aufgerufen, wenn Button 1 gedrückt wurde.
	 * (non-Javadoc)
	 * @see de.fuberlin.whitespace.regelbau.IButtonChangeListener#aktionchanged(java.lang.String)
	 */
	@Override
	public void aktionchanged(String oldbuttontext) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getContext().getResources().getStringArray(de.fuberlin.whitespace.regelbau.R.array.ListDummy));
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ebene0listener(satzanzeige, listview));
	}

	/*
	 * Wird aufgerufen, wenn Button 2 gedrückt wurde.
	 * (non-Javadoc)
	 * @see de.fuberlin.whitespace.regelbau.IButtonChangeListener#aktionoptionschanged(java.lang.String, java.lang.String)
	 */
	@Override 
	public void aktionoptionschanged(String text, String textVonButton1) {
	
		if(textVonButton1.contains("Zeig")){
			String[] elemente = {"Raststätten","Tankstellen","McDonalds","Subway"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new zeigemirebene1listener(satzanzeige, listview));
		}else if(textVonButton1.contains("Erinner")){
			String[] elemente = {"Sehenswürdigkeiten","Pause","Termin"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setOnItemClickListener(new erinneremichebene1listener(satzanzeige, listview));
			listview.setAdapter(adapter);
		}else if(textVonButton1.contains("Sende")){
			String[] elemente = {"SMS","E-Mail","WhatsApp","Rauchzeichen"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new sendemirebene1listener(satzanzeige,listview));
		}
	}

}
