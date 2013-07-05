package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class FuelConsumption extends Trigger {

    /**
     * 
     */
    private static final long serialVersionUID = -1781399220043459815L;

    @Override
    protected void onWakeUp() {
	this.subscribeDataObject("FuelConsumption");
    }

    @Override
    protected boolean isFullfilled(DataObject dataObject) {

	Param p = getParam("Threshold");
	Double value;

	if ("l/h".equals(p.unit())) {
	    value = (Double) dataObject.getElement("InstantaneousValuePerHour").getValue();
	} else {
	    value = (Double) dataObject.getElement("InstantaneousValuePerMilage").getValue();
	}

	if (value != null) {
	    return p.op(value);
	} else {
	    return false;
	}
    }

}
