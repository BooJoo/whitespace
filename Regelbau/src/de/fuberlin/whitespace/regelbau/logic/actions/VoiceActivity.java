package de.fuberlin.whitespace.regelbau.logic.actions;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.fuberlin.sms.SMSActivity;
import de.fuberlin.whitespace.kitt.Blinklicht;
import de.fuberlin.whitespace.kitt.KnightRider;
import de.fuberlin.whitespace.regelbau.R;

/**
 * Activity, die Sprach ein-/ausgabe behandelt und Textnachrichten anzeigen kann.
 * @author Christoph
 *
 */
public class VoiceActivity extends Activity implements VoiceActivityObserver {
	
	public static final String MAINTEXT = "maintext";
	public static final int REQUESTCODE = 384723984;
	
	TextView mainView;
	KnightRider rider;

	private Vector<VoiceActivityCallback> callbacks = new Vector<VoiceActivityCallback>();
 
	private VoiceActivityAbstractElement vol;
	public String text;
	public int duration = 3000;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	     VoiceActivityAbstractCallback vaacl = null;
	    rider = new KnightRider(this);
	    try{
			text = getIntent().getStringExtra("text");
	    }catch(Exception e){
	    	text = "Möchten sie, dass ich ihrer Freundin eine SMS sende?";
	    }
	    try{duration = getIntent().getIntExtra("duration",3000);}catch(Exception e){}
	    try{vaacl = (VoiceActivityAbstractCallback)getIntent().getSerializableExtra("abstractelement");}catch(Exception e){}
	    vol = new VoiceActivityAbstractElement(rider,this,vaacl) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 2773457465331868445L;

			@Override
			public void Ask(String text, int textSpeechTime) {
				rider.say(text);
				try {
					Thread.sleep(textSpeechTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				observer.add(this);
				// Hier die Spracheingabe Starten
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				//Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-DE");//"en-US"
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say your request!");
				intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
				startActivityForResult(intent, REQUESTCODE);
			}

			@Override
			public void evaluate(String[] matches,KnightRider rider2,Context con2) { 
				if(vaac == null)
					{String[] check1 = {"ja"};
				String[] check2 = {"nein"};
					if(containsAll(check1, matches)){
						// Schreibe SMS
						rider.say("OK Michael hier ist mein Vorschlag einer SMS");
						Intent i = new Intent(getApplicationContext(), SMSActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("text", "Schatzi ich bin noch mit den Jungs einen heben! Warte nicht wieder keifend!");
						bundle.putString("empfaenger", "smsto:015122822377");
						i.putExtras(bundle);
						startActivityForResult(i, 666);
						finish();
					}else if(containsAll(check2, matches)){
						rider.say("Ok. Ich habe verstanden. Sie lieben mich nicht mehr Michael");
						finish();
					}else{
						rider.say("Ich habe sie nicht verstanden, bitte wiederholen sie");
						Ask("",1500);
					}
					}else
					{
					   vaac.evaluate(matches, rider, mainView.getContext());	
					
					}
			}

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Ask(text,duration);
			}
		};
     	setContentView(R.layout.voiceactivity);
     	mainView = (TextView)findViewById(R.id.textView1);
     	((LinearLayout)mainView.getParent()).addView(new Blinklicht(this));
     	mainView.setText(text);
     	
     /*	if(savedInstanceState.getString(MAINTEXT) != null) 
     	{
     		   mainView.setText(savedInstanceState.getString(MAINTEXT));
     	}
     	else{
     		mainView.setText("Guraaaaandioooooossssooooo Message!");
     	}
     	*/
     	
     	
     	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startAsking(vol);
			}
		}).start();
    }
	
	/**
	 * Startet die Erste {@link VoiceActivityAbstractElement}
	 * @param abs
	 */
	public void startAsking(VoiceActivityAbstractElement abs){
		Thread th = new Thread(abs);
		th.start();
	}


	/**
	 * Prüft ob eine Liste von Wörtern in der Matches liste vorkommt.
	 * @param testwords Testwörter
	 * @param matches Alle gehörten Wörter
	 * @return true, wenn alle Wörter vorkommen, sonst false;
	 */
	public static boolean containsAll(String[] testwords,String[] matches){
		int i = 0;
		for(String word: testwords){
			for(int j = 0; j < matches.length; j++){
				if(matches[j].toLowerCase().contains(word.toLowerCase())){
					i++;
					j = matches.length+1;
				}
			}
		}
		if(i >= testwords.length) return true;
		else return false;
	}
	
	
	@Override
	public void add(VoiceActivityCallback vc) {
		callbacks.add(vc);
	}
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == REQUESTCODE){
			if(resultCode == RESULT_OK){
				 ArrayList<String> matches = data.getStringArrayListExtra(
			                RecognizerIntent.EXTRA_RESULTS);
				 String[] treffer = matches.toArray(new String[matches.size()]);
				 System.out.println("RES OK"+ treffer + " : "+callbacks.get(0));
				 if(treffer != null && callbacks.get(0) != null){
					 callbacks.get(0).evaluate(treffer,rider,this);
					 callbacks.removeElementAt(0);
				 }
			}
		}else{
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
