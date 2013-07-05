package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class OnceDriveDistance extends Trigger {

    /**
     * 
     */
    private static final long serialVersionUID = -1653813861082467609L;

    public double startOdometerState = -1;

    @Override
    protected void onWakeUp() {
	this.subscribeDataObject("Odometer");
    }

    @Override
    protected boolean isFullfilled(DataObject dataObject) {

	Double odometerValue = (Double) dataObject.getElement("Odometer").getValue();

	if (odometerValue == null) {
	    return false;
	}

	if(startOdometerState == -1) {
	    
	    startOdometerState = odometerValue;
	    
	} else {
	    if(Double.valueOf(this.getParam("Threshold").value()) < odometerValue - startOdometerState)
	    {
		startOdometerState = odometerValue;
		
		// fallAsleep sorgt dafür, dass dieser Auslöser bis zum nächsten Neustart der App nicht nochmal gestartet wird.
		this.fallAsleep();
		
		return true;
	    }
	}

	return false;
    }
}
