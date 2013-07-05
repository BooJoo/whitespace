package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class Rain extends Trigger {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2029817301527985410L;

    @Override
    protected void onWakeUp() {
	this.subscribeDataObject("RainIntensity");
    }

    @Override
    protected boolean isFullfilled(DataObject dataObject) {

	Double value = (Double) dataObject.getElement("RainIntensity").getValue();
	
	if (value == null || value.equals(0.0)) {
	    return false;
	} else {
	    return true;
	}
    }
    
}
