package de.fuberlin.whitespace.regelbau.logic.pool;

import java.util.LinkedList;
import java.util.List;

import android.os.Binder;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

/**
 * Der PoolBinder dient zur Kommunikation vom
 * vom UI-Thread in Richtung {@link RulesPool}-Service. 
 *
 */
public class PoolBinder extends Binder {
    
    private RulesPool pool;

    public PoolBinder (RulesPool rulesPool) {
	this.pool = rulesPool;
    }

    /**
     * Fügt eine neue Regel aus den gegebenen Triggern und Aktionen hinzu.
     * @param actions
     * @param trigger
     * @throws Exception falls <tt>actions</tt> oder <tt>trigger</tt> leer
     */
    public void AddRule(String name, LinkedList<Action> actions, LinkedList<Trigger> trigger ) throws Exception
    {
	if(actions.isEmpty())
	    throw new Exception("Keine Aktion vorhanden für die Ragel.");
	if(trigger.isEmpty())
	    throw new Exception("Keine Trigger vorhanden für die Ragel.");

	this.AddRule(new Rule(name,actions,trigger));
    }

    /**
     * Fügt <tt>rule</tt> zum Pool hinzu.
     * @param rule
     */
    public void AddRule(Rule rule) {
	this.pool.add(rule);
    }

    /**
     * Entfernen von Regel <tt>rule</tt> aus dem Pool.
     * @param rule
     */
    public void RemoveRule(Rule rule)
    {
	this.pool.remove(rule);
    }
    
    /**
     * Aktualisieren von <tt>rule</tt> im Pool.
     * @param rule
     */
    public void UpdateRule(Rule rule) {
	
	this.pool.update(rule);
    }
    
    public boolean containsRule (Rule rule) {
	return -1 != this.pool.rules.indexOf(rule);
    }
    
    public List<Rule> getAllRules () {
	return this.pool.rules;
    }

    public Rule getRuleById(long id) {
	
	for (Rule r : this.pool.rules) {
	    
	    assert(r.getId() != null);
	    
	    if (r.getId().equals(id)) {
		return r;
	    }
	}
	
	return null;
    }
    
    /**
     * Teilt dem Pool den Handler als zur
     * Kommunikation in Gegenrichtung mit.
     * @param handler
     */
    public void setHandler (PoolHandler handler) {
	this.pool.setHandler(handler);
    }
    
    /**
     * Setzt die Verbindung zum Proxy zurück und
     * verbindet neu zu <tt>address</tt>.
     * @param address
     */
    public void reconnect (String address) {
	this.pool.getClient().reinitialize(address);
    }
}
