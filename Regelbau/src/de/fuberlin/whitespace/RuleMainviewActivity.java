package de.fuberlin.whitespace;

import java.util.LinkedList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import de.fuberlin.whitespace.regelbau.MainActivity;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.RegelBearbeitenActivity;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.SQLDB.SQLDataBase;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;
import de.fuberlin.whitespace.regelbau.logic.actions.SpecialSprintMessage;

public class RuleMainviewActivity extends Activity {

    //MyAdapter myadapter;

	private RegelAdapter myregeladapter;
	private RulesPool rp;
	
	GridView gridview;
    private static RuleMainviewActivity _instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule_mainview);
		//myadapter = new MyAdapter(this,this);
		rp = new RulesPool(this); 
		
		myregeladapter = new RegelAdapter(this, android.R.layout.simple_list_item_1);
		
		Button addbutton = (Button) findViewById(R.id.addbutton);
		addbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rufeRegelkontruktionAuf();
			}
		});
		//LinkedList<Action> actions = new LinkedList<Action>();
		//LinkedList<Trigger> trigger = new LinkedList<Trigger>();
		
		//String[] tmp = {"Zeige mir Raststätten", "", "um 12 Uhr."};
	//	actions.add(new SpecialSprintMessage(this,tmp));	
	//		trigger.add(null);
	
	//	Rule regel = new Rule("KnightRider",actions, trigger);
	//	myregeladapter.add(regel);
		gridview = (GridView) findViewById(R.id.gridView1);
		for(Rule r: RulesPool.rules) myregeladapter.add(r);
		//myregeladapter.addAll(RulesPool.rules);
		gridview.setAdapter(myregeladapter);
		
		
		//gridview.setAdapter(myadapter);
		
		_instance = this;
    }


    public void rufeRegelkontruktionAuf(){
    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
    	startActivityForResult(i, 666);
    }
    
    public void rufeRegelkontruktionAuf(Rule rule){
    	
    	Intent i = new Intent(getApplicationContext(), RegelBearbeitenActivity.class);
    	Bundle b = new Bundle();
    	b.putSerializable("rule", rule); //Your id
    	i.putExtras(b); //Put your id to your next Intent
    	startActivityForResult(i, 666);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// Check which request we're responding to
	if (requestCode == 666) {
	    // Make sure the request was successful
	    if (resultCode == RESULT_OK) {

			// satzanzeige.setButtonLabelZwei(res);
			Rule rule = (Rule)data.getSerializableExtra("regel");
			boolean deleted = data.getBooleanExtra("deleted", false);
			// TODO: Datenbank einbinden anstatt des Adapters.
		/*	SQLDataBase sql = new SQLDataBase(this);
			LinkedList<byte[]> regeln = sql.getAllEntries();
			for(byte[] r: regeln){
				Rule regel = (Rule)r;
				
			}*/
			Vector<Rule> regeln = new Vector<Rule>();
			for(int i = 0 ; i < myregeladapter.getCount(); i++){
				Rule r = myregeladapter.getItem(i);
				if(r.getId() == rule.getId()){
					// Lass dieses Element in der neuen Liste aus und füge nach der Schleife das Neue hinzu.
					Toast.makeText(this, "Regel überschrieben.", Toast.LENGTH_SHORT).show();
				}
				else{
					regeln.add(r);
				}
			}
			if(!deleted){
				regeln.add(rule);
			}
			RulesPool.rules = new LinkedList<Rule>(regeln);
			rp.updateDB();
			
			myregeladapter = new RegelAdapter(this, android.R.layout.simple_list_item_1);
			for(Rule r: regeln) myregeladapter.add(r);
			//myregeladapter.addAll(regeln);
			gridview.setAdapter(myregeladapter);
			
			// Toast als Feedback
			//if(!deleted) Toast.makeText(this, "Regel gespeichert.", Toast.LENGTH_LONG).show();
			if(deleted) 
				Toast.makeText(this, "Regel gelöscht.", Toast.LENGTH_LONG).show();

	    }
	}
   }

    public static RuleMainviewActivity getInstance () {
    	return _instance;
    }

}
