package de.fuberlin.whitespace.regelbau;

public interface IRegelListener {
	/**
	 * Wird ausgelöst, wenn der Benutzer durch eine Eingabe die Regel verändert hat.
	 * @param rule
	 */
	public void RuleChanged(AbsRule rule);
}
