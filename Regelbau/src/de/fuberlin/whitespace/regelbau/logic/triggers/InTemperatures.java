package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.io.IOException;

import de.exlap.DataObject;
import de.exlap.ExlapException;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class InTemperatures extends Trigger {

	public boolean consumed=false;
	
	public InTemperatures() throws IllegalArgumentException, IOException, ExlapException
	{
		ProxyClient pc=ProxyClient.get();//instanz des Proxclients holen
		
		//Packete abonieren
		pc.addListener("InTemperature", this);//@TODO noch erstellen
		
	}
	
	
	@Override
	public void trigger(DataObject dataObject) {
		if(consumed)
		{
			if((Double)params.get("Operation")==1)
			{
				if((Double)params.get("Temperature") >=(Double)dataObject.getElement("InTemperature").getValue() )
				{

					consumed=false;
				}
			}
			else
			{
				if((Double)params.get("Operation")==0)
				{
					if((Double)params.get("Temperature") <=(Double)dataObject.getElement("InTemperature").getValue() )
					{

						consumed=false;
					}
				}
				else
				{
					if((Double)params.get("Temperature") !=(Double)dataObject.getElement("InTemperature").getValue() )
					{

						consumed=false;
					}
				}
			}
		}
		else
		{
			if((Double)params.get("Operation")==1)
			{
				if((Double)params.get("Temperature") <=(Double)dataObject.getElement("InTemperature").getValue() )
				{
					aktive =true;
					RulesPool.TriggerCall(this);
					consumed=true;
				}
			}
			else
			{

				if((Double)params.get("Operation")==0)
				{
					if((Double)params.get("Temperature") >=(Double)dataObject.getElement("InTemperature").getValue() )
					{
						aktive =true;
						RulesPool.TriggerCall(this);
						consumed=false;
					}
				}
				else
				{
					if((Double)params.get("Temperature") ==(Double)dataObject.getElement("InTemperature").getValue() )
					{
						aktive =true;
						RulesPool.TriggerCall(this);
						consumed=false;
					}
				}

			}
			
		}
		
	}

	

}
