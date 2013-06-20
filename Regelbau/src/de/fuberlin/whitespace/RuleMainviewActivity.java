package de.fuberlin.whitespace;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import de.fuberlin.whitespace.regelbau.MainActivity;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;
import de.fuberlin.whitespace.regelbau.logic.actions.SpecialSprintMessage;

public class RuleMainviewActivity extends Activity {

    MyAdapter myadapter;

	private RegelAdapter myregeladapter;

    private static RuleMainviewActivity _instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule_mainview);
		myadapter = new MyAdapter(this,this);
		myregeladapter = new RegelAdapter(this, android.R.layout.simple_list_item_1);
		Button addbutton = (Button) findViewById(R.id.addbutton);
		addbutton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			rufeRegelkontruktionAuf();
		}
	});
	LinkedList<Action> actions = new LinkedList<Action>();
	LinkedList<Trigger> trigger = new LinkedList<Trigger>();
	
	String[] tmp = {"Zeige mir Raststätten", "", "um 12 Uhr."};
	actions.add(new SpecialSprintMessage(this,tmp));	
		trigger.add(null);

	Rule regel = new Rule(actions, trigger);
	myregeladapter.add(regel);
	GridView gridview = (GridView) findViewById(R.id.gridView1);
	gridview.setAdapter(myregeladapter);
	
	
	//gridview.setAdapter(myadapter);
	
	_instance = this;
    }


    public void rufeRegelkontruktionAuf(){
	Intent i = new Intent(getApplicationContext(), MainActivity.class);
	startActivityForResult(i, 666);
    }
    
    public void rufeRegelkontruktionAuf(Rule rule){
    	
    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
		//String[] res = data.getStringArrayExtra("selectedItems");
		myregeladapter.add(rule);
		
		String[] res = ((ShowMessage)(rule.getActions().get(0))).getParameter();
		System.out.println(res[0]+" "+res[1]+" "+res[2]+" <- Name: "+res[3]);
		System.out.println(rule);
	    }
	}
    }

    public static RuleMainviewActivity getInstance () {
	return _instance;
    }

}
