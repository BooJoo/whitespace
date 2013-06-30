package de.fuberlin.whitespace.regelbau; 

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;

public class MainActivity extends Activity { // OoO

    Optionsanzeige optionsanzeigeListe;
    Satzanzeige satzanzeige;
    static Activity act;

    private DataLoader dataLoader;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(layout.regelbau);
	
	try {
	    this.dataLoader = new DataLoader(this);
	} catch (Throwable t) {
	    throw new RuntimeException(t);
	}
	
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
		new MyTextPicker(satzanzeige.getContext(), new MyNumberPickerCallback<String>() {

		    @Override
		    public void valueset(String value)  {

			System.out.println("Der Benutzer hat Regelname "+ value + " eingegeben");

			Intent intent = new Intent();

			//Bundle Objekt
			Bundle bundle = new Bundle();

			LinkedList<Action> actions = new LinkedList<Action>();
			LinkedList<Trigger> triggers = new LinkedList<Trigger>();

			actions.add(DataLoader.instantiateAction(satzanzeige.getActionVocabulary()));
			triggers.add(DataLoader.instantiateTrigger(satzanzeige.getTriggerVocabulary()));

			Rule regel = new Rule(value, actions, triggers);

			bundle.putSerializable("regel", regel);

			//Bundle zu intent hinzufügen
			intent.putExtras(bundle);

			if (getParent() == null) {
			    setResult(Activity.RESULT_OK, intent);
			} else {
			    getParent().setResult(Activity.RESULT_OK, intent);
			}
			
			finish();
		    }
		}, "Mein Regelname:");
	    }
	});
	
	act = this;	
    }
    
    public Satzanzeige getSatzanzeige(){
	return satzanzeige;
    }

}
