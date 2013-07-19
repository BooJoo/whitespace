package de.fuberlin.whitespace.regelbau.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ArgumentValue;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.pool.DataObjectListener;

/**
 * Stellt eine Schnittstelle für die Aktionen dar.
 * @author Stefan
 *
 */
public abstract class Action extends DataObjectListener implements Serializable {

    private transient Map<String, DataObject> dataObjectCache;

    /**
     * 
     */
    private static final long serialVersionUID = 7292784095129684509L;

    /**
     * enthält übergebene Parameter
     */
    private Map<String, List<String>> params;

    private String id;
    
    private String stringRepresentation;

    /**
     * regel zu der die Aktion gehört
     */
    private Rule parent;

    private boolean awake;
    
    /**
     * <i>Anmerkung:</i> Dieser Konstruktor wird von {@link DataLoader#instantiateAction} implizit
     * als existent vorausgesetzt.
     * @see DataLoader#instantiateAction(de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary)
     * @param id
     */
    public Action () {
	this.params = new HashMap<String, List<String>>();
	this.dataObjectCache = new HashMap<String, DataObject>();
	this.parent = null;
	this.stringRepresentation = null;
    }
    
    /**
     * Führt die Action Aus
     */
    public abstract void Do (Context ctx);
    
    /**
     *setzt einen Parameter  
     */
    public void setParam(String key, List<ArgumentValue> values) {
	
	List<String> rawValues = new ArrayList<String>();
	
	for (ArgumentValue val : values) {
	    rawValues.add(val.getCurrentValue());
	}
	
	params.put(key, rawValues);
    }
    
    @SuppressWarnings("serial")
    public void setParam (String name, final String value) {
	params.put(name, new ArrayList<String>(){{add(value);}});
    }
    
    public List<String> getParam (String name) {
	return this.params.get(name);
    }
    
    public String peekParam (String name) {
	if (params.containsKey(name) && params.get(name).size() > 0) {
	    return params.get(name).get(0);
	} else {
	    return null;
	}
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
    

    @Override
    public final void trigger(DataObject dataObject) {
	if (dataObject != null && dataObject.size() > 0) {
	    this.dataObjectCache.put(dataObject.getUrl(), dataObject);
	}
    }
    
    protected DataObject getDataObject(String dataObjectUrl) {
	return this.dataObjectCache.get(dataObjectUrl);
    }

    public void signalDestruction() {
	this.fallAsleep();
	this.parent = null;
    }

    /**
     * Diese Methode weckt die aktuelle Instanz auf und
     * führt {@link #onWakeUp()} aus.
     */
    public final void wakeUp () {
	
	this.awake = true;
	this.onWakeUp();
    }

    protected void onWakeUp() {
	
    }

    /**
     * Diese Methode versetzt die aktuelle Action-Instanz
     * in den Schlafzustand, hebt alle über {@link #subscribeDataObject(String)}
     * erfolgten Datenabonnements auf und ruft {@link #onFallAsleep()} auf.
     */
    public final void fallAsleep () {
	
	this.awake = false;
	
	super.tearDown(this.parent.getPool());
	
	this.onFallAsleep();
    }

    protected void onFallAsleep() {
	
    }

    /**
     * Über diese Methode können sich konkrete Implementierungen
     * für Datenobjekte mit der gegebenen URL anmelden.
     * @param dataObjectUrl
     */
    protected void subscribeDataObject (String dataObjectUrl) {
	
	if (this.isAwake()) {
	    super.subscribeDataObject(dataObjectUrl, this.parent.getPool());
	}
    }
    
    /**
     * Über diese Methode können konkrete Implementierungen
     * eine über {@link #subscribeDataObject(String)} erfolgte Anmeldung
     * für Datenobjekte mit der gegebenen URL wieder aufheben.
     * @param dataObjectUrl
     */
    protected void unsubscribeDataObject (String dataObjectUrl) {
	
	if (!this.isAwake()) {
	    super.subscribeDataObject(dataObjectUrl, this.parent.getPool());
	}
	
    }
    
    /**
     * Gibt <tt>true</tt> zurück, falls die aktuelle Instanz
     * im <i>awake</i>-Zustand ist, und <tt>false</tt> sonst
     * zurück.
     */
    public boolean isAwake () {
	return this.awake;
    }
    
    private synchronized void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
	s.defaultReadObject();
	this.dataObjectCache = new HashMap<String, DataObject>();
    }

}
