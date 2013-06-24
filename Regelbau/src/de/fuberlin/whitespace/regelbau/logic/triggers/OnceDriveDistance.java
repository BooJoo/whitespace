package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.io.IOException;

import de.exlap.DataObject;
import de.exlap.ExlapException;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class OnceDriveDistance  extends Trigger {

	
	public double startOdometerState=-1;
	public boolean enginOn=false;
	
	
	public OnceDriveDistance() throws IllegalArgumentException, IOException, ExlapException
	{
		subscribe();

	}
	public void subscribe()
	{
		ProxyClient pc=ProxyClient.get();//instanc des Proxclients holen
		
		//Packete abonieren
		pc.addListener("Odometer", this);
		pc.addListener("Motor status geändert (An 1/aus 0)", this);  //@TODO diese objekt anlegen
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
				if((Double)params.get("Sistance") < (Double)dataObject.getElement("Odometer").getValue()-startOdometerState)
				{
					aktive =true;
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
