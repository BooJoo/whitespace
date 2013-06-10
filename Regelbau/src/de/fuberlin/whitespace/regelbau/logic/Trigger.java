package de.fuberlin.whitespace.regelbau.logic;

import de.exlap.DataElement;
import de.fuberlin.whitespace.regelbau.logic.ProxyClient;

/**
 * Interface fuer die Ausloeser einer Regel
 * @author Stefan Lange
 */
public abstract class Trigger {

	
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
	public abstract void trigger (DataElement dataElement) ;
	
	@Override
	public boolean equals (Object o) {
	    return (o instanceof Trigger) && true; // @TODO: Implementierung offen
	}
    
	
	
	
	
}
