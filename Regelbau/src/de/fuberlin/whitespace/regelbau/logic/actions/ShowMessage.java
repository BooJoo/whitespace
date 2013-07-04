package de.fuberlin.whitespace.regelbau.logic.actions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class ShowMessage extends Action {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5113572234174380962L;
    
    @Override
    public void Do (Context context) {
	
	new AlertDialog.Builder(context)
		.setTitle("Erinnerung")
		.setMessage(String.valueOf(this.getParam("message")))
		.setNeutralButton(android.R.string.ok,
			new DialogInterface.OnClickListener() {

                	    @Override
                	    public void onClick(DialogInterface arg0,
                		    int arg1) {
                		
                	    }
			}
		).show();

    }
    
}
