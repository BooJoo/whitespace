package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Stellt eine Schnittstelle für Die Aktionen dar
 * @author Stefan<
 *
 */
public abstract class Action implements Serializable {

	/**
         * 
         */
        private static final long serialVersionUID = 7292784095129684506L;
        
	protected HashMap<String, Object> params;
	
	public void set (String name, Object value) {
		params.put(name, value);
	}
	/**
	 * Führt die Action Aus
	 */
	public abstract void Do();
}
