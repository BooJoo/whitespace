package de.fuberlin.whitespace.regelbau.logic;

/**
 * Interface fuer die Ausloeser einer Regel
 * @author Stefan Lange
 */
public interface Trigger {

	
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
	public void trigger(int i);
	
}
