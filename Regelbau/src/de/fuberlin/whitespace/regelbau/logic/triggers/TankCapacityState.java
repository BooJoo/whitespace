package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.io.IOException;

import de.exlap.DataElement;
import de.exlap.DataObject;
import de.exlap.ExlapException;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;
import de.fuberlin.whitespace.regelbau.logic.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public class TankCapacityState extends Trigger {

	boolean consumed=false;
	
	public TankCapacityState() throws IllegalArgumentException, IOException, ExlapException 
	{
		ProxyClient pc=ProxyClient.get();//instanc des Proxclients holen
		
		//Packete abonieren
		pc.addListener("TankLevel", this);
		
	}

	@Override
	public void trigger(DataObject dataObject) {
		double fuel=(Double)dataObject.getElement("Premium").getValue();
		
		if(consumed)
		{
			if((Double)params.get("TankState") > fuel)
			{
				consumed=false;
			}
		}
		else
		{
			if((Double)params.get("TankState") < fuel)
			{
				aktive =true;
				RulesPool.TriggerCall(this);
				consumed=true;
			}
		}
	}

}
