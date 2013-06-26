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
		
		
		System.out.println("speed1");
		subscribe();
		System.out.println("speed3");
	}

	
	public void subscribe()
	{
		System.out.println("");
//		ProxyClient pc=ProxyClient.get();//instanc des Proxclients holen
//		
//		//Packete abonieren
//		try {
//			pc.addListener("VehicleSpeed", this);
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExlapException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
