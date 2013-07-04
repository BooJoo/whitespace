package de.fuberlin.whitespace.regelbau.logic.actions;

import de.fuberlin.whitespace.regelbau.logic.Action;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * ruft beim ausl�sen die google map  app auf mit den derzeuitigen parametern
 * @author BEJEYOH
 *
 */
public class Gmap extends Action 
{
	
	/**
	 * ruft die Google Map App mit den parametern aus param auf
	 * @param context
	 */
	public void Do (Context context) {

		String uri = "geo:"+ "52.5069278, 13.3342062"+"?q="+((String)this.getParam("message"));
		context.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
		
	}
}
