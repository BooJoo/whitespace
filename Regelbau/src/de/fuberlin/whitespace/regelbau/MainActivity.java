package de.fuberlin.whitespace.regelbau; 

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import de.fuberlin.whitespace.regelbau.R.layout;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;

public class MainActivity extends Activity { // OoO
    	
	Optionsanzeige optionsanzeigeListe;
	Satzanzeige satzanzeige;
	static Activity act;
	
	private DataLoader dataLoader;
	//String[] tmp = {"Zeige mir", "Erinnere mich", "Informiere mich"};
	Rule r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_regelbau_main);
		
		
		try{
			r = (Rule)getIntent().getExtras().getSerializable("rule");
		}catch(Exception e){ r= null;}
		
		try {
		    this.dataLoader = new DataLoader(this);
		} catch (Throwable t) {
		    throw new RuntimeException(t);
		}
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainlayout);
		Button button1 = (Button) findViewById(R.id.buttoneins);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		ListView listview = (ListView) findViewById(R.id.listView1);
		
		satzanzeige = new Satzanzeige(this, dataLoader, null, button1, button2, button3);
		optionsanzeigeListe = new Optionsanzeige(this, listview, satzanzeige);
		
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
					new MyTextPicker(satzanzeige.getContext(), new MyNumberPickerCallback<String>() {
					
					@Override
					public void valueset(String value)  {
						System.out.println("Der Benutzer hat Regelname "+ value + " eingegeben");
						Intent intent = new Intent();
						//Bundle Objekt
						Bundle bundle = new Bundle();
						String[] output = {satzanzeige.getButtona().getText().toString(),satzanzeige.getButtonb().getText().toString(),satzanzeige.getButtonc().getText().toString(),value};
						LinkedList<Action> actions = new LinkedList<Action>();
						LinkedList<Trigger> trigger = new LinkedList<Trigger>();
						
						actions.add(new ShowMessage(output));
						
						trigger.add(null);
					
						Rule regel = new Rule(actions, trigger);
						
					   // bundle.putStringArray("selectedItems", output);
					    bundle.putSerializable("regel", regel);
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
				}, "Mein Regelname:");
			}
		});
		
		
		act = this;	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    System.out.println("Activity Result 1"); 
	    String[] res = data.getStringArrayExtra("selectedItems");
	   // satzanzeige.setButtonLabelZwei(res);
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
