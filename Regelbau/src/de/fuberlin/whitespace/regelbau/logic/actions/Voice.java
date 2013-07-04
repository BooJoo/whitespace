package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import de.fuberlin.sms.SMSActivity;
import de.fuberlin.whitespace.kitt.KnightRider;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class Voice extends Action {

    /**
     * 
     */
    private static final long serialVersionUID = 4411413357505979997L;

    @Override
    public void Do (Context context) {
    	KnightRider rider = new KnightRider(context);
    	Intent i = new Intent(context,VoiceActivity.class);
    	Bundle bundle = new Bundle();
		bundle.putString("text", "Möchten sie, dass ich Ihrer Freundin Murkelpaulinchen eine SMS schicke?");
		bundle.putInt("duration", 4000);
		bundle.putSerializable("abstractelement",new VoiceActivityAbstractCallback(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 4931186229068717396L;
			@Override
			public void evaluate(String[] heardElements, KnightRider rider,
					Context context) {
				String[] tmp = {"Papier"};
				String[] check1 = {"Ja"};
				if(VoiceActivity.containsAll(tmp, heardElements))
				{
					rider.say("Sie sind mir ja einer!");
				}else if(VoiceActivity.containsAll(check1, heardElements)){
						// Schreibe SMS
						rider.say("OK Michael hier ist mein Vorschlag einer SMS");
						Intent i = new Intent(context, SMSActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("text", "Schatzi ich bin noch mit den Jungs einen heben! Warte nicht wieder keifend!");
						bundle.putString("empfaenger", "smsto:015122822377");
						i.putExtras(bundle);
						context.startActivity(i);
				
					}
				else{
					rider.say("Manchmal höre ich den Marder in mir drinne Husten. ");
				}
			}
		});
		i.putExtras(bundle);
    	context.startActivity(i);

    }

}
