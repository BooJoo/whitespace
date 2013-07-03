package de.fuberlin.whitespace.regelbau.logic.actions;

public interface VoiceActivityCallback {
	/**
	 * Überprüft auf bestimmte Codewörter und definiert weiteres Verhalten
	 * @param heardElements Rückgabe der Spracheingabe
	 */
	public void evaluate(String[] heardElements);
}
