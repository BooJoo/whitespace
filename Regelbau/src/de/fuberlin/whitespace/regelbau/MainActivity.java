package de.fuberlin.whitespace.regelbau; 

import java.util.LinkedList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.pool.PoolBinder;
import de.fuberlin.whitespace.regelbau.logic.pool.RulesPool;

public class MainActivity extends Activity { // OoO

    Optionsanzeige optionsanzeigeListe;
    Satzanzeige satzanzeige;
    static Activity act;

    private DataLoader dataLoader;
    
    private ServiceConnection poolConnection;
    private PoolBinder pool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(layout.activity_regelbau_main);
	
	try {
	    this.dataLoader = new DataLoader(this);
	} catch (Throwable t) {
	    throw new RuntimeException(t);
	}

	this.poolConnection = new ServiceConnection () {

	    @Override
	    public void onServiceConnected(ComponentName name, IBinder service) {
		pool = (PoolBinder) service;
	    }

	    @Override
	    public void onServiceDisconnected(ComponentName name) {
		pool = null;
	    }

	};
	
	Button button1 = (Button) findViewById(R.id.buttoneins);
	Button button2 = (Button) findViewById(R.id.button2);
	Button button3 = (Button) findViewById(R.id.button3);
	Button button4 = (Button) findViewById(R.id.button4);
	
	ListView listview = (ListView) findViewById(R.id.listView1);

	satzanzeige = new Satzanzeige(this, dataLoader, null, button1, button2, button3);
	optionsanzeigeListe = new Optionsanzeige(this, listview, satzanzeige);
	
	// Action und Trigger erzeugen und als Activity Result an Aufrufer zurückgeben
	button4.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		new MyTextPicker(satzanzeige.getContext(), new MyPickerCallback<String>() {

		    @Override
		    public void valueset(String value)  {
			
			Intent intent = new Intent();
			
			LinkedList<Action> actions = new LinkedList<Action>();
			LinkedList<Trigger> triggers = new LinkedList<Trigger>();

			actions.add(DataLoader.instantiateAction(satzanzeige.getActionVocabulary()));
			triggers.add(DataLoader.instantiateTrigger(satzanzeige.getTriggerVocabulary()));
			
			Rule r = new Rule(value, actions, triggers);
			MainActivity.this.pool.AddRule(r);
			
			//Bundle zu intent hinzufügen
			intent.putExtra("rule_id", 1L);

			if (getParent() == null) {
			    setResult(Activity.RESULT_OK, intent);
			} else {
			    getParent().setResult(Activity.RESULT_OK, intent);
			}
			
			finish();
		    }
		}, "Regelname:",
		"Meine neue Regel");
	    }
	});
	
	act = this;	
    }
    
    @Override
    protected void onResume() {
	
	super.onResume();
	this.bindService(
		new Intent(this, RulesPool.class),
		this.poolConnection,
		Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onPause() {
	
	super.onPause();
	this.unbindService(this.poolConnection);
    }
    
    public Satzanzeige getSatzanzeige(){
	return satzanzeige;
    }

}
