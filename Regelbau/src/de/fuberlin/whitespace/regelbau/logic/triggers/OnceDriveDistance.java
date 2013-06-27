package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.io.IOException;

import de.exlap.DataObject;
import de.exlap.ExlapException;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class OnceDriveDistance extends Trigger {

    	/**
    	 * 
     	*/
    	private static final long serialVersionUID = -1653813861082467609L;
    
	public double startOdometerState=-1;
	public boolean enginOn=false;
	
	
	public OnceDriveDistance() throws IllegalArgumentException, IOException, ExlapException
	{
		subscribe("Odometer");
		subscribe("Motor status geändert (An 1/aus 0)"); //@TODO dieses objekt anlegen

	}
	
	@Override
	public void trigger(DataObject dataObject) 
	{
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
					aktiviert =true;
					RulesPool.TriggerCall(this);
					startOdometerState=(Double)dataObject.getElement("Odometer").getValue();
				}
			}
		}
		else
		{
			enginOn = (Boolean)dataObject.getElement("State").getValue();
		}
		
	}

	

}
