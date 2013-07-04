package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public abstract class AbstractThresholdTrigger extends Trigger {
    
    /**
     * 
     */
    private static final long serialVersionUID = -612870447126007581L;
    
    protected abstract String getThresholdObjectUrl ();
    
    @Override
    protected void onWakeUp() {
	
	this.subscribeDataObject(this.getThresholdObjectUrl());
    }

    @Override
    protected boolean isFullfilled (DataObject dataObject) {
	
	return this.getParam("Threshold").op((Double) dataObject.getElement(this.getThresholdObjectUrl()).getValue());
    }
    
}
