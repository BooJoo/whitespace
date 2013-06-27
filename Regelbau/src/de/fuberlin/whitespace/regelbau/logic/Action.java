package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;

/**
 * Stellt eine Schnittstelle für Die Aktionen dar
 * @author Stefan<
 *
 */
public abstract class Action implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7292784095129684509L;

    private Map<String, String> params;

    private String id;
    
    private String stringRepresentation;
    
    /**
     * <i>Anmerkung:</i> Dieser Konstruktor wird von {@link DataLoader#instantiateAction} implizit
     * als existent vorausgesetzt.
     * @see DataLoader#instantiateAction(de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary)
     * @param id
     */
    public Action () {
	this.params = new HashMap<String, String>();
	this.stringRepresentation = null;
    }

    public void setParam (String name, String value) {
	params.put(name, value);
    }
    
    public Object getParam (String name) {
	return params.get(name);
    }
    
    public String getId() {
	return this.id;
    }
    
    public void setId (String id) {
	this.id = id;
    }
    
    public void setStringRepresentation (String str) {
	this.stringRepresentation = str;
    }
    
    /**
     * Führt die Action Aus
     */
    public abstract void Do();
    
    @Override
    public String toString () {
	return this.stringRepresentation;
    }

}
