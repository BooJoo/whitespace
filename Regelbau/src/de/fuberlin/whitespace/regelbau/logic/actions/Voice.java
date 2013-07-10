package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import de.fuberlin.whitespace.kitt.KnightRider;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.sms.SMSActivity;

public class Voice extends Action {

    /**
     * 
     */
    private static final long serialVersionUID = 4411413357505979997L;

    @Override
    public void Do (Context context) {
    	KnightRider rider = new KnightRider(context);
    	Intent i = new Intent(context,VoiceActivity.class);
    	final String blubb = (String) this.getParam("message");
    	Bundle bundle = new Bundle();
		bundle.putString("text", "Möchten Sie eine SMS schreiben?");
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
						rider.say("Okay Meikel wählen Sie einen Empfänger aus. Danach liefer ich Ihnen einen Textvorschlag.");
						Intent i = new Intent(context, SMSActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("text", blubb);
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
