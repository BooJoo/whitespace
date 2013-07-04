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
	    
	    double fuel=(Double)dataObject.getElement("Premium").getValue();
	    double threshold = Integer.valueOf(this.getParam("Threshold").value()) * 0.1;

	    if(consumed)
	    {
		if(fuel > threshold)
		{
		    consumed=false;
		}
	    }
	    else
	    {
		if(fuel < threshold)
		{
		    consumed=true;
		    return true;
		}
	    }
	    
	    return false;
	}
	
}
