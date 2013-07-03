package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import android.content.Intent;
import de.fuberlin.sms.SMSActivity;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class SMS extends Action {

	private static final long serialVersionUID = 5335575425776041388L;
	
	Context context;
    
    public SMS(Context context){
    	this.context = context;
    }
    
	@Override
	public void Do() {
		Intent i = new Intent(context, SMSActivity.class);
    	context.startActivity(i);

	}

}
