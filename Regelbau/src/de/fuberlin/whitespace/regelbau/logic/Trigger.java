package de.fuberlin.whitespace.regelbau.logic;

import java.util.HashMap;

import de.exlap.DataElement;
import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;

/**
 * Interface fuer die Ausloeser einer Regel
 * @author Stefan Lange
 */
public abstract class Trigger {

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
		params.put(name, value);
	}
	
	@Override
	public boolean equals (Object o) {
	    return (o instanceof Trigger) && true; // @TODO: Implementierung offen
	}
    
	
	
	
	
}
