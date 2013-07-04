package de.fuberlin.whitespace.regelbau.logic.actions;

import java.io.Serializable;

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
public abstract class VoiceActivityAbstractElement implements VoiceActivityCallback, Runnable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7864769562913889823L;
	KnightRider rider;VoiceActivityObserver observer;
	protected VoiceActivityAbstractCallback vaac;
	public VoiceActivityAbstractElement(KnightRider rider, VoiceActivityObserver vobs){
		this.rider = rider;
		this.observer = vobs;
	}
	
	public VoiceActivityAbstractElement(KnightRider rider,VoiceActivityObserver vobs, VoiceActivityAbstractCallback vaac){
		this.rider = rider;
		this.observer = vobs;
		this.vaac = vaac;
	}
	/**
	 * Stellt eine Frage
	 * @param text 
	 * @param textSpeechTime So lange wird der Computer brauchen den Text vorzulesen.
	 */
	public abstract void Ask(String text,int textSpeechTime);
}
