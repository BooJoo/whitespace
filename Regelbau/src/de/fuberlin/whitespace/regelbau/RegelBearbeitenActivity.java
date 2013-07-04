package de.fuberlin.whitespace.regelbau;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;
import de.fuberlin.whitespace.regelbau.logic.pool.PoolBinder;
import de.fuberlin.whitespace.regelbau.logic.pool.RulesPool;

public class RegelBearbeitenActivity extends Activity {
    
    private Rule r = null;
    private Satzanzeige satzanzeige;

    private DataLoader dataLoader;

    private ServiceConnection poolConnection;
    private PoolBinder pool;

    EditText text;
    
    @SuppressWarnings("unused")
    private Optionsanzeige optionsanzeigeListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(layout.activity_regelbau_edit);
	
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

	this.poolConnection = new ServiceConnection () {

	    @Override
	    public void onServiceConnected(ComponentName name, IBinder service) {
		pool = (PoolBinder) service;
		r = pool.getRuleById(getIntent().getExtras().getLong("rule_id"));
		initUI();
	    }

	    @Override
	    public void onServiceDisconnected(ComponentName name) {
		pool = null;
	    }

	};
    }
    
    @Override
    protected void onResume() {
	
	super.onResume();
	this.bindService(
		new Intent(this, RulesPool.class),
		this.poolConnection,
		Context.BIND_WAIVE_PRIORITY);
    }
    
    @Override
    protected void onPause() {
	
	super.onPause();
	this.unbindService(this.poolConnection);
    }

    protected void initUI() {
	
	Button button1 = (Button) findViewById(R.id.buttoneins);
	Button button2 = (Button) findViewById(R.id.button2);
	Button button3 = (Button) findViewById(R.id.button3);
	ImageButton button4 = (ImageButton) findViewById(R.id.button4);
	ImageButton buttonDelete = (ImageButton) findViewById(R.id.buttondelete);

	text = (EditText) findViewById(R.id.editTextRegelname);

	final ListView listview = (ListView) findViewById(R.id.listView1);
	final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);

	cb1.setChecked(r.isActive());
	cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (!isChecked) {
		    Toast.makeText(listview.getContext(), "Regel deaktiviert.", Toast.LENGTH_SHORT).show();
		} else {
		    Toast.makeText(listview.getContext(), "Regel aktiviert.", Toast.LENGTH_SHORT).show();
		}

		r.setActive(isChecked);
	    }
	});

	satzanzeige = new Satzanzeige(this, this.dataLoader , null, button1, button2, button3);
	optionsanzeigeListe = new Optionsanzeige(this, listview, satzanzeige);

	if(r != null){	  
	    
	    Drawable img = this.getResources().getDrawable( drawable.mycheck );

	    // Datenstrukturen für Regelerstellung erzeugen
	    ActionVocabulary actionVocab = satzanzeige.getDataLoader().getActionVocabulatoryByAction(r.getActions().get(0));
	    TriggerVocabulary triggerVocab = satzanzeige.getDataLoader().getTriggerVocabularyByTrigger(r.getTriggers().get(0));

	    satzanzeige.setActionVocabulary(actionVocab);
	    satzanzeige.setTriggerVocabulary(triggerVocab);

	    // Button-Labels initialisieren
	    button1.setText(actionVocab.getSelectedActionString());
	    button2.setText(actionVocab.getSelectedActionOptionsString());
	    button3.setText(triggerVocab.getSelectedTriggerString());
	    
	    button1.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    button2.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    button3.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	}

	// Speichern

	button4.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
		
		Intent intent = new Intent();

		String mytext = text.getText().toString();
		System.out.println("Der Benutzer hat Regelname "+ mytext + " eingegeben");

		// Trigger aus den Auswahlinformationen in ActionVocabulary und TriggerVocabulary erzeugen
		DataLoader.updateAction(r.getActions().get(0), satzanzeige.getActionVocabulary());
		DataLoader.updateTrigger(r.getTriggers().get(0), satzanzeige.getTriggerVocabulary());

		r.setName(mytext);

		RegelBearbeitenActivity.this.pool.UpdateRule(r);
		
		intent.putExtra("deleted", false);

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
		Intent intent = new Intent();

		RegelBearbeitenActivity.this.pool.RemoveRule(r);

		// Löschung signalisieren
		intent.putExtra("deleted", true);

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
