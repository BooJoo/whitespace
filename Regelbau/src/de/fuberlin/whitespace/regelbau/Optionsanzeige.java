package de.fuberlin.whitespace.regelbau;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene0listener;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene1listener_erinneremich;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene1listener_informieremich;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene1listener_zeigemir;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene2und3listener_auto;

/**
 * Klasse zum Anzeigen der unterschiedlichen Optionen des Regelbaus
 * @author Christoph, Maria
 *
 */
public class Optionsanzeige extends LinearLayout implements IButtonChangeListener{

	private String[] options;
	ListView listview;
	OptionsanzeigeListAdapter myListAdapter;
	Satzanzeige satzanzeige;
    MainActivity mainActivity;
	
	public Optionsanzeige(Context context, String[] options, ListView list, Satzanzeige satzanzeige) {
		super(context);
		this.options = options;
		this.satzanzeige = satzanzeige;
		satzanzeige.registerForButtonChanges(this);
		listview = list;
		mainActivity = (MainActivity) context;
	}
	
	public void setNewElements(String[] elemente){
		myListAdapter.setNewElements(elemente);
	}
	
	
	// Hier werden die Listenelemente angezeigt
	public void init(){
		for(int i = 0; i < options.length; i++)
			System.out.println(""+options[i]);
	}

	/** Wird aufgerufen, wenn Button 3 gedrückt wurde. @see de.fuberlin.whitespace.regelbau.IButtonChangeListener#ausloeserchanged(java.lang.String, java.lang.String, java.lang.String)*/
	@Override
	public void ausloeserchanged(String oldbuttontext,String textVonButton1, String textVonButton2) {
		String[] elemente = getResources().getStringArray(R.array.Button3Array);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, elemente);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ebene2und3listener_auto(satzanzeige, listview));
	}

	/** Wird aufgerufen, wenn Button 1 gedrückt wurde.*/
	
	@Override
	public void aktionchanged(String oldbuttontext) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getContext().getResources().getStringArray(de.fuberlin.whitespace.regelbau.R.array.Button1Array));
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene0listener(satzanzeige, listview));
	}

	/** Wird aufgerufen, wenn Button 2 gedrückt wurde.*/
	@Override 
	public void aktionoptionschanged(String text, String textVonButton1) {
		String[] elemente = new String[Integer.SIZE];
		
		if(textVonButton1.contains("Zeig")){
			elemente = getResources().getStringArray(R.array.Button2WennZeigeMirArray);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene1listener_zeigemir(satzanzeige, listview));
		}
		else if(textVonButton1.contains("Erinner")){
			elemente = getResources().getStringArray(R.array.Button2WennErinnereMichArray);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene1listener_erinneremich(satzanzeige, listview));
		}
		else if(textVonButton1.contains("Informier")){
			elemente = getResources().getStringArray(R.array.Button2WennInformiereMichArray);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(listview.getContext(),android.R.layout.simple_list_item_1,elemente);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ebene1listener_informieremich(satzanzeige, listview));
		}	
	}

}
