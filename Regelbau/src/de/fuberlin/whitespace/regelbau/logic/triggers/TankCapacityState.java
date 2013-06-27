package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.io.IOException;

import de.exlap.DataObject;
import de.exlap.ExlapException;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class TankCapacityState extends Trigger {

	/**
	 * 
	 */
    	private static final long serialVersionUID = -498620383371469712L;
    	
	boolean consumed=false;
	
	public TankCapacityState() throws IllegalArgumentException, IOException, ExlapException
	{
		this.subscribe("TankLevel");
	}
	
	@Override
	public void trigger(DataObject dataObject) {
	    
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
				aktiviert =true;
				RulesPool.TriggerCall(this);
				consumed=true;
			}
		}
	}

}
