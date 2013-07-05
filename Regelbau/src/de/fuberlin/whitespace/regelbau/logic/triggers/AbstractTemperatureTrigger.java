package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;

public abstract class AbstractTemperatureTrigger extends AbstractThresholdTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 2481141504467477486L;

    @Override
    protected boolean isFullfilled (DataObject dataObject) {

	Param p;
	Double value = (Double) dataObject.getElement(this.getThresholdObjectUrl()).getValue();

	if (value == null) {
	    return false;
	}

	p = this.getParam("Threshold");

	if ("°F".equals(p.unit())) {
	    value = value * 1.8 + 32;
	}

	return p.op(value);
    }
}
