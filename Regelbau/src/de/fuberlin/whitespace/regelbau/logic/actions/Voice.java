package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import android.content.Intent;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class Voice extends Action {

    /**
     * 
     */
    private static final long serialVersionUID = 4411413357505979997L;

    @Override
    public void Do (Context context) {
    	Intent i = new Intent(context,VoiceActivity.class);
    	context.startActivity(i);

    }

}
