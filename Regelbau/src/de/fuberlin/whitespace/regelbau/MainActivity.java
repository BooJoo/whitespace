package de.fuberlin.whitespace.regelbau; 

import java.util.LinkedList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.SQLDB.SQLDataBase;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;
import de.fuberlin.whitespace.regelbau.logic.triggers.Speed;

public class MainActivity extends Activity { // OoO
	Optionsanzeige optionsanzeigeListe;
	Satzanzeige satzanzeige;
	static Activity act;
	String[] tmp = {"Zeige mir", "Erinnere mich", "Informiere mich"};
	Rule r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_regelbau_main);
		
		
		try{
			r = (Rule)getIntent().getExtras().getSerializable("rule");
		}catch(Exception e){ r= null;}
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainlayout);
		Button button1 = (Button) findViewById(R.id.buttoneins);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		ListView listview = (ListView) findViewById(R.id.listView1);
		
		satzanzeige = new Satzanzeige(this, null, button1, button2, button3);
		optionsanzeigeListe = new Optionsanzeige(this,tmp,listview,satzanzeige);
		//satzanzeige.setPadding(20, 20, 20, 40);
		if(r != null){
			ShowMessage s = (ShowMessage)r.getActions().get(0);
			String[] res = s.getParameter();
			button1.setText(res[0]);
			button2.setText(res[1]);
			button3.setText(res[2]);
			System.out.println("Bearbeite Regel:"+res[0]+" "+res[1]);
		}
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					new MyTextPicker(satzanzeige.getContext(), new MyNumberPickerCallback() {
					
					@Override
					public void valueset(String value)  {
						System.out.println("Methode erreicht");
						System.out.println("Der Benutzer hat Regelname "+ value + " eingegeben");
						Intent intent = new Intent();
						//Bundle Objekt
						
						
						Bundle bundle = new Bundle();
						String[] output = {satzanzeige.getButtona().getText().toString(),satzanzeige.getButtonb().getText().toString(),satzanzeige.getButtonc().getText().toString(),value};
						LinkedList<Action> actions = new LinkedList<Action>();
						LinkedList<Trigger> trigger = new LinkedList<Trigger>();
						
						actions.add(new ShowMessage(output));
						try{
					    
						Trigger t1= new Speed();
						
						t1.set("Speed", 50);
						t1.set("Operation", 0);
						
						trigger.add(t1);
					    
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
							
					   
						Rule regel = new Rule(value,actions, trigger);
						try {
							RulesPool.AddRule(" sas ", actions, trigger);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    // bundle.putStringArray("selectedItems", output);
					    bundle.putSerializable("regel", regel);
					    try{
					    	
					    
					    }
					    catch(Exception e)
					    {
					    	System.out.println(e.getMessage());
					    }
					    //	bundle.putAll(map)
						
						//Bundle zu intent hinzufügen
						intent.putExtras(bundle);
						if (getParent() == null) {
						    setResult(Activity.RESULT_OK, intent);
						} else {
						    getParent().setResult(Activity.RESULT_OK, intent);
						}
						finish();
					}
					
					@Override
					public void valueset(int value) {
						// TODO Auto-generated method stub
						
					}
				}, "Mein Regelname:");
			}
		});
		
		
		act = this;	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 System.out.println("Activity Result 1"); 
	     String[] res = data.getStringArrayExtra("selectedItems");
	     satzanzeige.setButtonLabelZwei(res);
	     System.out.println("Activity Result kam an");
	     if (requestCode == 1337) {
	        // Make sure the request was successful 
	    }
	}
	
	public void rufeActivityAuf(String[] elemente){
//		MultiSelectListViewActivity.elemente = elemente;
//		Intent i = new Intent(this, multiselect_with_checkboxes.MultiSelectListViewActivity.class);
//		startActivityForResult(i, 1337);
//		//context.startActivity(i); 
	}
	
	public Satzanzeige getSatzanzeige(){
		return satzanzeige;
	}

}
