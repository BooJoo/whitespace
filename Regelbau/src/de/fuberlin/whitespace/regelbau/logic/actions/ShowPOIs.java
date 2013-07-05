package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class ShowPOIs extends Action {

    /**
     * 
     */
    private static final long serialVersionUID = 2675178116801138054L;

    @Override
    public void Do (Context context) {
	String uri = "geo:"+ "52.5069278, 13.3342062"+"?q="+ ((String)this.getParam("message")) ;
	context.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
    }

}
