package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.Context;
import de.fuberlin.whitespace.regelbau.logic.pool.RulesPool;

/**
 * Stellt eine Regel aus ein oder mehreren Auslösern (Trigger) und Aktionen
 * @author BJO
 *
 */
public class Rule implements Serializable {


    private static final long serialVersionUID = 2280704049072286645L;
    private String name;
    private boolean active = true;
    
    private Long id;

    private LinkedList<Trigger> trigger;
    private LinkedList<Action> actions;
    
    /**
     * die letzten bekannten Zustände der {@link Trigger}
     * der Regel: <tt>true</tt> (erfüllt) oder <tt>false</tt> (nicht erfüllt)
     */
    private Map<Trigger, Boolean> triggerStates;
    
    /**
     * Anzahl der aktuell erfüllten {@link Trigger} dieser Instanz
     */
    private int fullfilledTriggerCount;
    
    /**
     * Signalisiert, ob die aktuelle Regel gelöscht wurde
     */
    private boolean deleted;
    
    /**
     * aktueller Zustand: awake (<tt>true</tt>) oder asleep (<tt>false</tt>)
     */
    private boolean awake;
    
    /**
     * Pool, in dem diese Regel sich befindet
     */
    private transient RulesPool pool;
    
    /**
     * Konstruktor
     * @param actions Erwartet Linkedlist von den Aktionen die Ausgeführt werden sollen
     * @param trigger erwartet Linkedlist von den Triggern für für die Regel
     */
    public Rule(String name, LinkedList<Action> actions, LinkedList<Trigger> trigger )
    {
	this(name, null, actions, trigger);
    }

    public Rule(String name, Long id, LinkedList<Action> actions, LinkedList<Trigger> trigger )
    {
	this.id = id;
	this.name = name;
	this.awake = false;
	this.deleted = false;
	this.pool = null;
	
	this.trigger = new LinkedList<Trigger>();
	this.actions = new LinkedList<Action>();
	
	this.fullfilledTriggerCount = 0;
	this.triggerStates = new HashMap<Trigger,Boolean>();
	
	this.setActions(actions);
	this.setTriggers(trigger);
    }
    
    /**
     * Diese Methode kann genutzt werden um eine Zustandsänderung (erfüllt oder
     * nicht erfüllt) des gegebenen {@link Trigger}s <tt>t</tt> bekannt zu machen.
     * Der Zustand eines Triggers ist durch die Rückgabe von {@link Trigger#getState()} definiert.<br /><br />
     * Ein Aufruf aktualisiert die Informationen der Rule-Instanz und führt (falls alle
     * Trigger erfüllt sind) ggf. die Aktionen aus.<br />
     * @param t
     */
    public void signalTriggerStateChange (Trigger t) {
	
	boolean isFullfilled = t.getState();
	
	if (this.trigger.contains(t) && !this.triggerStates.get(t).equals(isFullfilled)) {
	    
	    this.triggerStates.put(t, isFullfilled);
	    this.fullfilledTriggerCount += isFullfilled ? 1 : -1;
	    
	    if (this.fullfilledTriggerCount == this.trigger.size()) {
		
		final Context context = this.pool.getHandler().getContext();
		
		for (final Action a : this.actions) {
		    this.pool.getHandler().post(new Runnable () {

			@Override
			public void run() {
			    a.Do(context);
			}
			
		    });
		}
	    }
	}
    }
    
    /**
     * Versetzt die aktuelle Instanz in den <i>awake</i>-Zustand
     * und delegiert den Aufruf durch {@link Trigger#wakeUp()}
     * an die zugehöriggen Trigger.
     * @param rulesPool
     */
    public void wakeUp (RulesPool rulesPool) {
	
	if (!this.awake && this.active) {
	    this.pool = rulesPool;
	    this.wakeUp();
	}
    }
    
    private void wakeUp () {
	
	if (!this.awake && this.active) {
	    this.awake = true;

	    for (Trigger t : this.trigger) {
		t.wakeUp();
	    }
	}
    }
    
    /**
     * Versetzt die aktuelle Instanz in den <i>asleep</i>-Zustand,
     * setzt die intern gespeicherten Trigger-Zustandsinformationen
     * zurück und delegiert den Aufruf durch {@link Trigger#fallAsleep()}
     * an die zugehöriggen Trigger.
     */
    public void fallAsleep () {
	
	if (this.awake) {
	    this.awake = false;

	    for (Trigger t : this.trigger) {
		t.fallAsleep();
		this.triggerStates.put(t, false);
	    }
	}
	
    }
    
    /**
     * Gibt <tt>true</tt> zurück, falls diese Instanz im
     * <i>awake</i>-Zustand ist, <tt>false</tt> sonst.
     */
    public boolean isAwake () {
	return this.awake;
    }
    
    /**
     * Signalisiert die Löschung dieser Regel. Interne
     * Zustandsinformationen werden zurückgesetzt, alle
     * Trigger und Aktionen entfernt sowie der Aufruf
     * über {@link Trigger#signalDestruction()} an
     * die zugehörigen Trigger weitergeleitet.
     */
    public void signalDestruction() {
	this.clear();
	this.setDeleted(true);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    /**
     * Gibt die Id zurück, falls sie durch {@link #setId(Long)} gesetzt wurde.
     * Andernfalls gibt <tt>null</tt> zurück.
     */
    public Long getId() {
	return id;
    }
    
    /**
     * Legt die Id dieser Instanz fest.
     * @param id
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * Gibt <tt>true</tt> zurück, falls diese Regel
     * aktiv ist, <tt>false</tt> sonst.
     * @return
     */
    public boolean isActive() {
	return active;
    }
    
    /**
     * De-/Aktiviert diese Regel
     * @param active
     */
    public void setActive(boolean active) {

	boolean oldState = this.active;

	if (oldState != active) {
	    this.active = active;

	    if (!active) {
		this.fallAsleep();
	    } else {
		this.wakeUp();
	    }
	}
    }

    /**
     * Gibt den {@link RulesPool} zurück, in dem 
     * diese Instanz ist.
     */
    public RulesPool getPool() {
        return pool;
    }

    public LinkedList<Trigger> getTriggers() {
	return trigger;
    }

    public void setTriggers(LinkedList<Trigger> trigger) {
	for (Trigger t : this.trigger) {
	    this.removeTrigger(t);
	}
	
	for (Trigger t : trigger) {
	    this.addTrigger(t);
	}
    }

    public void addTrigger(Trigger t) {
	this.trigger.add(t);
	this.triggerStates.put(t, t.getState());
	t.setParent(this);
    }
    
    public void removeTrigger(Trigger t) {
	this.trigger.remove(t);
	this.triggerStates.remove(t);

	if (t.getState()) {
	    this.fullfilledTriggerCount--;
	}
	
	t.signalDestruction();
    }
    
    public void addAction(Action a) {
	this.actions.add(a);
	a.setParent(this);
    }
    
    public void removeAction(Action a) {
	this.actions.remove(a);
    }

    private void clear() {
	for (Trigger t : this.trigger) {
	    this.removeTrigger(t);
	}
	
	for (Action a : this.actions) {
	    this.removeAction(a);
	}
    }
    
    public LinkedList<Action> getActions() {
	return actions;
    }

    public void setActions(LinkedList<Action> actions) {
	for (Action a : this.actions) {
	    this.removeAction(a);
	}
	
	for (Action a : actions) {
	    this.addAction(a);
	}
    }
    
    public void setDeleted (boolean deleted) {
	this.deleted = deleted;
    }
    
    public boolean isDeleted() {
	return this.deleted;
    }
    
    /**
     * Zwei {@link Rule}-Instanzen sind gleich, falls ihre Ids nicht <tt>null</tt> und gleich sind.
     * @see #getId()
     */
    @Override
    public boolean equals (Object o) {
	return (o instanceof Rule) && this.id != null && this.id.equals(((Rule) o).id);
    }
}
