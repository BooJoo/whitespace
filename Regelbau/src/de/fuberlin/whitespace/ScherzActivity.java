package de.fuberlin.whitespace;

import java.util.ArrayList;
import java.util.LinkedList;

import de.fuberlin.whitespace.regelbau.MainActivity;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.actions.SpecialSprintMessage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class ScherzActivity extends Activity {
	private static final int SPEECH_REQUEST_CODE = 432985;
	KnightRider rider;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
     	setContentView(R.layout.knightrider);
     	Button b = (Button) findViewById(R.id.button1);
     	b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendRecognizeIntent();
			}
		});
     	rider = new KnightRider(this);
     	//sendRecognizeIntent();
    }
	
	private void sendRecognizeIntent() {
		rider.say("Möchten sie eine Regel erstellen?");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		//Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-DE");//"en-US"
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say your request!");
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
		startActivityForResult(intent, SPEECH_REQUEST_CODE);
		
	}
	
	public boolean containsAll(String[] testwords,ArrayList<String> matches){
		int i = 1;
		for(String word: testwords){
			for(int j = 0; j < matches.size(); j++){
				if(matches.get(j).contains(word)){
					i++;
					j = matches.size()+1;
				}
			}
		}
		System.out.println("containsAll: "+i+"testwords.length: "+testwords.length);
		if(i >= testwords.length) return true;
		else return false;
	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			 ArrayList<String> matches = data.getStringArrayListExtra(
		                RecognizerIntent.EXTRA_RESULTS);
		 
			 String alles ="";
			 String[] tmp = {"wie","wird","wetter"};
			 String[] tmp2 = {"mama","geburtstag"};
			 boolean verstanden = false;
			 for(String s: matches){ alles+=s;
			    if(s.toLowerCase().contains("ja")){
			    	Intent i = new Intent(this,MainActivity.class);
			    	startActivity(i);
			    	verstanden = true;
			    	break;
			    }else if(s.toLowerCase().contains("nein")){
			    	verstanden = true;
			    	break;
			    }
			    else if(s.toLowerCase().contains("arschloch")){
			    	verstanden = true;
			    	rider.say("Noch so'n Spruch: Kieferbruch! Selber!");
			    	break;
			    }
			    else if(s.toLowerCase().contains("klappe")){
			    	verstanden = true;
			    	rider.say("Ich entscheide immer noch selber, wann ich Rede!");// Alle meine Entchen schwimmen auf dem SEEEEEEEEEEEEEE, schwimmen auf dem SEEEEEEEEEEE. Ich kann den ganzen Tag so weitermachen wenn du nochmal so einen Spruch ablässt freundchen. ");
			    	break;
			    }
			    else if(containsAll(tmp, matches)){
			    	verstanden = true;
			    	rider.say("Wetter wird heute supi! Geh schwimmen!");
			    	break;
			    }
			    else if(containsAll(tmp2, matches)){
			    	verstanden = true;
			    	rider.say("Deine Mutter hat am 24.5.2019  Geburtstag!");
			    	break;
			    }
			 }
			 if(!verstanden) {
				 rider.say("Sprich deutlich du Otto! Ich bin die wunderschöne nait Indastriess tu sausend, wenn ich dich nicht verstehe, tut es niemand!");
			 }
			// rider.say(alles);
			//message = message.toLowerCase();
		

			
		}
	}

}
