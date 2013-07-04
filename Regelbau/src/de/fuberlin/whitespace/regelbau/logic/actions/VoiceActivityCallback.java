package de.fuberlin.whitespace.regelbau.logic.actions;

import android.content.Context;
import de.fuberlin.whitespace.kitt.KnightRider;

public interface VoiceActivityCallback{
	/**
	 * Überprüft auf bestimmte Codewörter und definiert weiteres Verhalten
	 * @param heardElements Rückgabe der Spracheingabe
	 */
	public void evaluate(String[] heardElements,KnightRider rider,Context context);
}
