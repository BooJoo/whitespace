package de.fuberlin.whitespace.regelbau.logic.actions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import de.fuberlin.whitespace.RuleMainview;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class ShowMessage extends Action {

    private String[] parameter;
    
    public ShowMessage(String[] args) {
	// TODO Auto-generated method stub
	this.parameter = args;
    }

    public String[] getParameter() {
	return parameter;
    }

    @Override
    public void Do() {
	
	new AlertDialog.Builder(RuleMainview.getInstance())
		.setTitle("Erinnerung")
		.setMessage((String) parameter[1])
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
