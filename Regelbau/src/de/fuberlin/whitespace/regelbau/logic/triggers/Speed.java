package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.io.IOException;

import de.exlap.DataObject;
import de.exlap.ExlapException;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class Speed extends Trigger {

	public boolean consumed=false;
	
	
	public Speed() throws IllegalArgumentException, IOException, ExlapException
	{
		
		subscribe();
	}

	
	public void subscribe()
	{
		ProxyClient pc=ProxyClient.get();//instanc des Proxclients holen
		
		//Packete abonieren
		pc.addListener("VehicleSpeed", this);
	}
	
	@Override
	public void trigger(DataObject dataObject) 
	{
		if(consumed)
		{
			if((Double)params.get("Operation")==1)
			{
				if((Double)params.get("Speed") >=(Double)dataObject.getElement("VehicleSpeed").getValue() )
				{

					consumed=false;
				}
			}
			else
			{
				if((Double)params.get("Speed") <=(Double)dataObject.getElement("VehicleSpeed").getValue() )
				{

					consumed=false;
				}
			}
		}
		else
		{
			if((Double)params.get("Operation")==1)
			{
			if((Double)params.get("Speed") <=(Double)dataObject.getElement("VehicleSpeed").getValue() )
			{
				aktive =true;
				RulesPool.TriggerCall(this);
				consumed=true;
			}
			}
			else
			{
				if((Double)params.get("Speed") >=(Double)dataObject.getElement("VehicleSpeed").getValue() )
				{
					aktive =true;
					RulesPool.TriggerCall(this);
					consumed=true;
				}
			}
		}
		
		
		
		
	}

}
