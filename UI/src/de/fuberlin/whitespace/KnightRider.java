package de.fuberlin.whitespace;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class KnightRider implements OnInitListener {
	
	private Context context;
	TextToSpeech tts;
	public KnightRider(Context c){
		this.context = c;
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		c.startActivity(checkIntent);

		tts = new TextToSpeech(c,this);
		
	}
	
	public void say(String s){
		tts.speak("Ich spüre eine große Menge an Snickas im Handschuhfach. Teilen ist nicht mehr optional. Snickas oder Schleudersitz.", TextToSpeech.QUEUE_ADD, null);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			 
            int result = tts.setLanguage(Locale.GERMAN);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
             
             say("");
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
	}
}
