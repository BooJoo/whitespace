package de.fuberlin.whitespace.regelbau.logic.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.exlap.DataObject;

/**
 * Ein DataObjectListener abonniert und empfängt Datenpakete
 * vom {@link ProxyClient}.
 *
 */
public abstract class DataObjectListener {
    
    /**
     * Liste der Abonnements
     */
    private List<String> dataObjectSubscriptions;
    
    public DataObjectListener () {
	this.dataObjectSubscriptions = new ArrayList<String>();
    }
    
    /**
     * Diese Methode nimmt die Datenobjekte vom {@link ProxyClient}
     * entgegen.
     * @param dataObject
     */
    public abstract void trigger(DataObject dataObject);

    /**
     * Über diese Methode können sich konkrete Implementierungen für 
     * Datenobjekte mit der gegebenen URL anmelden.
     * @param dataObjectUrl
     */
    protected void subscribeDataObject(String dataObjectUrl, RulesPool pool) {
    
        if (pool != null & !this.dataObjectSubscriptions.contains(dataObjectUrl)) {
            try {
        	pool.getClient().addListener(dataObjectUrl, this);
        	this.dataObjectSubscriptions.add(dataObjectUrl);
            } catch (Throwable t) {
        	throw new RuntimeException(t);
            }
        }
        
    }

    /**
     * Über diese Methode können konkrete Implementierungen
     * eine über {@link #subscribeDataObject(String)} erfolgte Anmeldung
     * für Datenobjekte mit der gegebenen URL wieder aufheben.
     * @param dataObjectUrl
     */
    protected void unsubscribeDataObject(String dataObjectUrl, RulesPool pool) {
        
        if (pool != null && this.dataObjectSubscriptions.contains(dataObjectUrl)) {
            try {
        	pool.getClient().removeListener(dataObjectUrl, this);
        	this.dataObjectSubscriptions.remove(dataObjectUrl);
            } catch (Throwable t) {
        	throw new RuntimeException(t);
            }
        }
    }
    
    /**
     * Meldet alle Datenabonnements wieder ab.
     * @param pool
     */
    protected void tearDown (RulesPool pool) {
	
	List<String> urls = new ArrayList<String>();	
	for (String url : this.dataObjectSubscriptions) {
	    urls.add(url);
	}
	
	for (String dataObjectUrl : urls) {
	    this.unsubscribeDataObject(dataObjectUrl, pool);
	}
    }

}
