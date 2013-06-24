package de.fuberlin.whitespace.regelbau;

import java.util.LinkedList;

import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;
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
	String[] tmp = {"Zeige mir", "Erinnere mich", "Informiere mich"};
	private Optionsanzeige optionsanzeigeListe;
	boolean deleted = false;
	EditText text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_regelbau_edit);
		
		//Tastatur standardmäßig ausblenden
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isActive())
    		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    	
		
		try{
			r = (Rule)getIntent().getExtras().getSerializable("rule");
		}catch(Exception e){ r= null;}
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainlayout);
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
				String mytext = text.getText().toString();
				System.out.println("Der Benutzer hat Regelname "+ mytext + " eingegeben");
				Intent intent = new Intent();
				//Bundle Objekt
				Bundle bundle = new Bundle();
				String[] output = {satzanzeige.getButtona().getText().toString(),satzanzeige.getButtonb().getText().toString(),satzanzeige.getButtonc().getText().toString(),text.getText().toString()};
				LinkedList<Action> actions = new LinkedList<Action>();
				LinkedList<Trigger> trigger = new LinkedList<Trigger>();
				
				actions.add(new ShowMessage(output));
				
					trigger.add(null);
				//Toast.makeText(this, "Regel überschrieben", Toast.LENGTH_LONG);
				Rule regel = new Rule(mytext, r.getId(),actions, trigger);
				regel.setActive(r.isActive());
			   // bundle.putStringArray("selectedItems", output);
			    bundle.putSerializable("regel", regel);
			    bundle.putBoolean("deleted", deleted);
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
					
		
		});
		
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    deleted = true;
				Intent intent = new Intent();
				//Bundle Objekt
				Bundle bundle = new Bundle();

				
				Rule regel = new Rule(r.getName(),r.getId(),null, null);
				
			   // bundle.putStringArray("selectedItems", output);
			    bundle.putSerializable("regel", regel);
			    bundle.putBoolean("deleted", deleted);
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
		});
	}
}
