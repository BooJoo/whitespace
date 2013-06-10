package de.fuberlin.whitespace.regelbau.logic;

import java.util.HashMap;

/**
 * Stellt eine Schnittstelle für Die Aktionen dar
 * @author Stefan<
 *
 */
public abstract class Action {

	protected HashMap<String, Object> params;
	
	public void set (String name, Object value) {
		params.put(name, value);
	}
	/**
	 * Führt die Action Aus
	 */
	public abstract void Do();
}
