package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class TankCapacityState extends Trigger {

	/**
	 * 
	 */
    	private static final long serialVersionUID = -498620383371469712L;
    	
	boolean consumed=false;
	
	@Override
	protected void onWakeUp() {
	    this.subscribeDataObject("TankLevel");
	}
	
	@Override
	protected boolean isFullfilled (DataObject dataObject) {
	    
	    Double threshold = null;
	    Double fuel = null;
	    Double premium = (Double) dataObject.getElement("Premium").getValue();
	    Double diesel = (Double) dataObject.getElement("Diesel").getValue();
	    
	    if (diesel == null && premium == null) {
		return false;
	    }
	    
	    threshold = Integer.valueOf(this.getParam("Threshold").value()) * 0.1;
	    
	    if (premium != null) {
		fuel = premium;
	    } else if (diesel != null) {
		fuel = diesel;
	    }
	    
	    if(consumed) {
		if(fuel > threshold) {
		    consumed = false;
		}
	    } else {
		if(fuel < threshold) {
		    consumed = true;
		    return true;
		}
	    }
	    
	    return false;
	}
	
}
