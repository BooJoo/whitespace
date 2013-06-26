package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.HashMap;

import de.exlap.DataElement;
import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;

/**
 * Interface fuer die Ausloeser einer Regel
 * @author Stefan Lange
 */
public abstract class Trigger implements Serializable {

	/**
         * 
         */
        private static final long serialVersionUID = -3386299757013046952L;
        
	protected HashMap<String, Object> params;
	
	
	
	/**
	 * gibt an ob der Trigger derzeitig erfüllt ist oder nicht 
	 */
	public boolean aktive=false;

	/**
	 * Nimmt Parkete Entgegen für die es sich vorher bei ProxyClient Angemeldet hat,
	 * Und Entscheidet an denen ob Sich der Status (boolean aktive) veraendert
	 * geschieht dieses, so muss RulesPool.TriggerActive(this); aufgerufen werden
	 * @param i Platzhalter fuer Parketdifinition
	 */
	public abstract void trigger (DataObject dataObject) ;
	
	public void set (String name, Object value) {
		System.out.println("erreicht2.1");
		if(params==null)
		{
		params= new HashMap<String, Object>();
		}
		System.out.println("ereicht 2,5");
		params.put(name, value);
	}
	
	@Override
	public boolean equals (Object o) {
	    return (o instanceof Trigger) && true; // @TODO: Implementierung offen
	}
    
	public abstract void subscribe();
	
	
	
	
}
