package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
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
public abstract class VoiceActivityAbstractElement implements VoiceActivityCallback, Runnable{
	KnightRider rider;VoiceActivityObserver observer;
	public VoiceActivityAbstractElement(KnightRider rider, VoiceActivityObserver vobs){
		this.rider = rider;
		this.observer = vobs;
	}
	
	/**
	 * Stellt eine Frage
	 * @param text 
	 * @param textSpeechTime So lange wird der Computer brauchen den Text vorzulesen.
	 */
	public abstract void Ask(String text,int textSpeechTime);
}
