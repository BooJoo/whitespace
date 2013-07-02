package de.fuberlin.whitespace.regelbau.logic.actions;

import de.fuberlin.whitespace.kitt.KnightRider;

/**
 * Stellt einen Knoten im Zustandsgraphen des Fragens dar.
 * Von einem Element kann es entweder zu einem weiteren Frageelement kommen, oder 
 * das Fragen beendet werden.
 * 
 * @author Christoph
 * @see de.fuberlin.whitespace.logic.actions.VoiceActivity
 * @see de.fuberlin.whitespace.logic.actions.Voice
 */
public abstract class VoiceActivityAbstractElement {
	/**
	 * Definiert das Verhalten des Elementes.
	 * @param rider
	 */
	public abstract void Ask(KnightRider rider);
}
