package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public abstract class AbstractThresholdTrigger extends Trigger {
    
    /**
     * 
     */
    private static final long serialVersionUID = -612870447126007581L;

    public AbstractThresholdTrigger () {
	this.subscribe(this.getThresholdObjectUrl());
    }
    
    public abstract String getThresholdObjectUrl ();

    @Override
    public void trigger (DataObject dataObject) {
	
	boolean aktivierung = this.getParam("Threshold").op((Double) dataObject.getElement(this.getThresholdObjectUrl()).getValue());
	
	if (!aktiviert && aktivierung) {
	    RulesPool.TriggerCall(this);
	}
	
	aktiviert = aktivierung;
    }

}
