package de.fuberlin.whitespace.regelbau; 

import multiselect_with_checkboxes.MultiSelectListViewActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import de.fuberlin.whitespace.regelbau.R.layout;

public class Main extends Activity { // OoO
	Optionsanzeige optionsanzeigeListe;
	Satzanzeige satzanzeige;
	static Activity act;
	String[] tmp = {"Zeige mir", "Erinnere mich", "Sende"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    satzanzeige = new Satzanzeige(this, null);
		satzanzeige.setPadding(20, 20, 20, 40);
		optionsanzeigeListe = new Optionsanzeige(this,tmp,satzanzeige);
		Button okbutton = new Button(this);
		okbutton.setText("Regel speichern");
		setContentView(layout.activity_regelbau_main);
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainlayout);
		layout.addView(satzanzeige);
		layout.addView(optionsanzeigeListe);
		layout.addView(okbutton);
		act = this;	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 System.out.println("Activity Result 1"); 
		 //Check which request we're responding to
		 //tmp = data.getStringArrayExtra("selectedItems");
	     String[] res = data.getStringArrayExtra("selectedItems");
	     satzanzeige.setButtonLabelZwei(res);
	     System.out.println("Activity Result kam an");
	     if (requestCode == 1337) {
	        // Make sure the request was successful
	      
	    }
	}
	
	public void rufeActivityAuf(String[] elemente){
		MultiSelectListViewActivity.elemente = elemente;
		Intent i = new Intent(this, multiselect_with_checkboxes.MultiSelectListViewActivity.class);
		startActivityForResult(i, 1337);
		//context.startActivity(i); 
	}
	
	public Satzanzeige getSatzanzeige(){
		return satzanzeige;
	}

}
