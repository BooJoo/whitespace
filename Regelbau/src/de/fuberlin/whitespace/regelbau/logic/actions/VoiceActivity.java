package de.fuberlin.whitespace.regelbau.logic.actions;

import de.fuberlin.whitespace.kitt.Blinklicht;
import de.fuberlin.whitespace.kitt.KnightRider;
import de.fuberlin.whitespace.regelbau.R;
import android.app.Activity;
import android.os.Bundle; 
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Activity, die Sprach ein-/ausgabe behandelt und Textnachrichten anzeigen kann.
 * @author Christoph
 *
 */
public class VoiceActivity extends Activity {
	
	public static final String MAINTEXT = "maintext";
	
	TextView mainView;
	KnightRider rider;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
     	setContentView(R.layout.voiceactivity);
     	mainView = (TextView)findViewById(R.id.textView1);
     	((LinearLayout)mainView.getParent()).addView(new Blinklicht(this));
     /*	if(savedInstanceState.getString(MAINTEXT) != null) 
     	{
     		   mainView.setText(savedInstanceState.getString(MAINTEXT));
     	}
     	else{
     		mainView.setText("Guraaaaandioooooossssooooo Message!");
     	}
     	*/
     	
     	rider = new KnightRider(this);
     	startAsking(new VoiceActivityAbstractElement() {
			
			@Override
			public void Ask(KnightRider rider) {
				
			}
		});
    }
	
	public void startAsking(VoiceActivityAbstractElement abs){
		abs.Ask(rider);
	}
}
