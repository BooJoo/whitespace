package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;

/**
 * Stellt eine Schnittstelle für die Aktionen dar.
 * @author Stefan
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

    private Rule parent;
    
    /**
     * <i>Anmerkung:</i> Dieser Konstruktor wird von {@link DataLoader#instantiateAction} implizit
     * als existent vorausgesetzt.
     * @see DataLoader#instantiateAction(de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary)
     * @param id
     */
    public Action () {
	this.params = new HashMap<String, String>();
	this.parent = null;
	this.stringRepresentation = null;
    }
    
    /**
     * Führt die Action Aus
     */
    public abstract void Do (Context ctx);
    
    public void setParam (String name, String value) {
	params.put(name, value);
    }
    
    public String getParam (String name) {
	return params.get(name);
    }
    
    public void removeParam(String paramName) {
	this.params.remove(paramName);
    }
    
    public boolean hasParam (String name) {
	return params.containsKey(name);
    }
    
    public List<String> getParamNames () {
	ArrayList<String> result = new ArrayList<String>();
	result.addAll(this.params.keySet());
	return result;
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
    
    public Rule getParent () {
	return this.parent;
    }
    
    public void setParent(Rule rule) {
	this.parent = rule;
    }
    
    @Override
    public String toString () {
	return this.stringRepresentation;
    }

}
