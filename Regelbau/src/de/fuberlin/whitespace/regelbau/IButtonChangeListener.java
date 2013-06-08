package de.fuberlin.whitespace.regelbau;

public interface IButtonChangeListener {
	public void ausloeserchanged(String oldbuttontext,String aktion, String aktionoptionen);
	public void aktionchanged(String oldbuttontext);
	public void aktionoptionschanged(String text,String aktion);
}
