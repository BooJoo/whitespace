package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import android.content.Intent;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.sms.SMSActivity;

public class SMS extends Action {

    private static final long serialVersionUID = 5335575425776041388L;

    @Override
    public void Do (Context context) {
	Intent i = new Intent(context, SMSActivity.class);
	context.startActivity(i);

    }

}
