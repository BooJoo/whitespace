package de.fuberlin.whitespace.regelbau.logic.actions;

public interface VoiceActivityObserver {
	/**
	 * Ein callback wird registriert. Wurde dieser ausgelöst,
	 * wird er automatisch ausgetragen.
	 * @param vc
	 */
	public void add(VoiceActivityCallback vc);
}
