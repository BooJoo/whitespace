package de.fuberlin.whitespace.regelbau;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene0listener;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene1listener;
import de.fuberlin.whitespace.regelbau.listenlogik.ebene2listener;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ActionOption;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerGroup;

/**
 * Klasse zum Anzeigen der unterschiedlichen Optionen des Regelbaus
 * @author Christoph, Maria
 *
 */
public class Optionsanzeige extends LinearLayout implements IButtonChangeListener{

    ListView listview;
    Satzanzeige satzanzeige;
    MainActivity mainActivity;

    public Optionsanzeige(Context context, ListView list, Satzanzeige satzanzeige) {
	super(context);
	this.satzanzeige = satzanzeige;
	satzanzeige.registerForButtonChanges(this);
	listview = list;
	mainActivity = (MainActivity) context;
    }
    
    // Hier werden die Listenelemente angezeigt
    public void init(){

	List<ActionVocabulary> vocab = this.satzanzeige.getDataLoader().getAllActionVocabularies();

	for (int i = 0; i < vocab.size(); i++) {
	    System.out.println(vocab.get(i).getWord());
	}
    }

    /** Wird aufgerufen, wenn Button 3 gedrückt wurde. @see de.fuberlin.whitespace.regelbau.IButtonChangeListener#ausloeserchanged(java.lang.String, java.lang.String, java.lang.String)*/
    @Override
    public void ausloeserchanged(String oldbuttontext,String textVonButton1, String textVonButton2) {

	ArrayAdapter<TriggerGroup> adapter = new ArrayAdapter<TriggerGroup>(getContext(),android.R.layout.simple_list_item_1,
		satzanzeige.getDataLoader().getTriggerGroupsByActionVocabulary(satzanzeige.getCurrentActionVocabulary()));
	listview.setAdapter(adapter);
	listview.setOnItemClickListener(new ebene2listener(satzanzeige, listview));
    }

    /** Wird aufgerufen, wenn Button 1 gedrückt wurde.*/

    @Override
    public void aktionchanged(String oldbuttontext) {
	ArrayAdapter<ActionVocabulary> adapter = new ArrayAdapter<ActionVocabulary>(getContext(),android.R.layout.simple_list_item_1,this.satzanzeige.getDataLoader().getAllActionVocabularies());
	listview.setAdapter(adapter);
	listview.setOnItemClickListener(new ebene0listener(satzanzeige, listview));
    }

    /** Wird aufgerufen, wenn Button 2 gedrückt wurde.*/
    @Override 
    public void aktionoptionschanged(String text, String textVonButton1) {
	
	int layout;
	ArrayAdapter<ActionOption> adapter;
	
	ActionOption.clearSelection(satzanzeige.getCurrentActionVocabulary());
	
	if (satzanzeige.getCurrentActionVocabulary().isMultipleChoice()) {
	    listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    layout = android.R.layout.simple_list_item_multiple_choice;
	} else {
	    listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	    layout = android.R.layout.simple_list_item_1;
	}

	adapter = new ArrayAdapter<ActionOption>(
		listview.getContext(), 
		layout,
		satzanzeige.getCurrentActionVocabulary().getOptions());

	listview.setAdapter(adapter);
	listview.setOnItemClickListener(new ebene1listener(satzanzeige, listview));

    }

}
