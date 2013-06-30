package de.fuberlin.whitespace.regelbau;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class RegelBearbeitenActivity extends Activity {

    private Rule r;
    private Satzanzeige satzanzeige;
    
    private DataLoader dataLoader;

    private Optionsanzeige optionsanzeigeListe;
    boolean deleted = false;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(layout.regelbau_edit);
	
	try {
	    this.dataLoader = new DataLoader(this);
	} catch (Throwable t) {
	    throw new RuntimeException(t);
	}

	//Tastatur standardmäßig ausblenden
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	if(imm.isActive()) {
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	try{
	    r = (Rule)getIntent().getExtras().getSerializable("rule");
	}catch(Exception e){ r= null;}

	Button button1 = (Button) findViewById(R.id.buttoneins);
	Button button2 = (Button) findViewById(R.id.button2);
	Button button3 = (Button) findViewById(R.id.button3);
	ImageButton button4 = (ImageButton) findViewById(R.id.button4);
	ImageButton buttonDelete = (ImageButton) findViewById(R.id.buttondelete);

	text = (EditText) findViewById(R.id.editTextRegelname);
	text.setText(r.getName());

	final ListView listview = (ListView) findViewById(R.id.listView1);
	final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
	//Toast.makeText(listview.getContext(), ""+r.isActive(), Toast.LENGTH_SHORT).show();

	cb1.setChecked(r.isActive());
	cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(!isChecked) Toast.makeText(listview.getContext(), "Regel deaktiviert.", Toast.LENGTH_SHORT).show();
		else Toast.makeText(listview.getContext(), "Regel aktiviert.", Toast.LENGTH_SHORT).show();
		r.setActive(isChecked);
	    }
	});

	satzanzeige = new Satzanzeige(this, this.dataLoader , null, button1, button2, button3);
	optionsanzeigeListe = new Optionsanzeige(this,listview,satzanzeige);

	if(r != null){	    
	    
	    // Datenstrukturen für Regelerstellung erzeugen
	    ActionVocabulary actionVocab = satzanzeige.getDataLoader().getActionVocabulatoryByAction(r.getActions().get(0));
	    TriggerVocabulary triggerVocab = satzanzeige.getDataLoader().getTriggerVocabularyByTrigger(r.getTriggers().get(0));
	    
	    satzanzeige.setActionVocabulary(actionVocab);
	    satzanzeige.setTriggerVocabulary(triggerVocab);
	    
	    // Button-Labels initialisieren
	    satzanzeige.getButtona().setText(actionVocab.getSelectedActionString());
	    satzanzeige.getButtonb().setText(actionVocab.getSelectedActionOptionsString());
	    satzanzeige.getButtonc().setText(triggerVocab.getSelectedTriggerString());
	    
	    //System.out.println("Bearbeite Regel:"+res[0]+" "+res[1]);
	}
	
	// Speichern
	
	button4.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
		
		Rule regel;
		
		String mytext = text.getText().toString();
		System.out.println("Der Benutzer hat Regelname "+ mytext + " eingegeben");
		
		Intent intent = new Intent();
		
		//Bundle Objekt
		Bundle bundle = new Bundle();
		
		LinkedList<Action> actions = new LinkedList<Action>();
		LinkedList<Trigger> triggers = new LinkedList<Trigger>();
		
		// Trigger aus den Auswahlinformationen in ActionVocabulary und TriggerVocabulary erzeugen
		actions.add(DataLoader.instantiateAction(satzanzeige.getActionVocabulary()));
		triggers.add(DataLoader.instantiateTrigger(satzanzeige.getTriggerVocabulary()));
		
		//Toast.makeText(this, "Regel überschrieben", Toast.LENGTH_LONG);
		
		regel = new Rule(mytext, r.getId(), actions, triggers);
		regel.setActive(r.isActive());
		bundle.putSerializable("regel", regel);
		bundle.putBoolean("deleted", deleted);
		
		//Bundle zu intent hinzufügen
		intent.putExtras(bundle);
		
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, intent);
		} else {
		    getParent().setResult(Activity.RESULT_OK, intent);
		}
		
		finish();
	    }


	});

	// Löschen
	
	buttonDelete.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		deleted = true;
		Intent intent = new Intent();
		
		//Bundle Objekt
		Bundle bundle = new Bundle();
		Rule regel = new Rule(r.getName(),r.getId(),null, null);
		
		bundle.putSerializable("regel", regel);
		bundle.putBoolean("deleted", deleted);
		
		//Bundle zu intent hinzufügen
		intent.putExtras(bundle);
		
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, intent);
		} else {
		    getParent().setResult(Activity.RESULT_OK, intent);
		}
		
		finish();
	    }
	});
    }
}
