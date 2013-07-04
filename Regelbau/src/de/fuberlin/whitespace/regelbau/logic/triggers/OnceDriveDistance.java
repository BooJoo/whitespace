package de.fuberlin.whitespace.regelbau.logic.triggers;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class OnceDriveDistance extends Trigger {

    	/**
    	 * 
     	*/
    	private static final long serialVersionUID = -1653813861082467609L;
    
	public double startOdometerState=-1;
	public boolean enginOn=false;
	
	@Override
	protected void onWakeUp() {
	    this.subscribeDataObject("Odometer");
	    this.subscribeDataObject("EngineOn"); // @TODO Objekt im Interface anlegen
	}

	@Override
	protected boolean isFullfilled(DataObject dataObject) {
	    
	    if(dataObject.getUrl().equals("Odometer"))
	    {
		if(enginOn && startOdometerState==-1)
		{
		    startOdometerState =(Double)dataObject.getElement("Odometer").getValue();
		}
		else
		{
		    if(Double.valueOf(this.getParam("Threshold").value()) < (Double)dataObject.getElement("Odometer").getValue()-startOdometerState)
		    {
			startOdometerState=(Double)dataObject.getElement("Odometer").getValue();
			return true;
		    }
		}
	    }
	    else
	    {
		enginOn = (Boolean)dataObject.getElement("State").getValue();
	    }
	    
	    return false;
	}
}
