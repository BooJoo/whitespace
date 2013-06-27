package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.LinkedList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stellt eine Regel aus ein oder mehreren Auslösern(Trigger) und Aktionen
 * @author BJO
 *
 */
public class Rule implements Serializable {


    private static final long serialVersionUID = 2280704049072286645L;
    String name;
    boolean active = true;
    double id;



    LinkedList<Trigger> trigger;// = new LinkedList<Trigger>();
    LinkedList<Action> actions;// = new LinkedList<Action>();

    /**
     * Konstruktor
     * @param actions Erwartet Linkedlist von den Aktionen die Ausgeführt werden sollen
     * @param trigger erwartet Linkedlist von den Triggern für für die Regel
     */
    public Rule(String name,LinkedList<Action> actions, LinkedList<Trigger> trigger )
    {
	// trigger = new LinkedList<Trigger>();
	//	 actions = new LinkedList<Action>();
	id = Math.random();
	this.name = name;
	this.actions = actions;
	this.trigger = trigger;
    }

    public Rule(String name,double id,LinkedList<Action> actions, LinkedList<Trigger> trigger )
    {
	// trigger = new LinkedList<Trigger>();
	//	 actions = new LinkedList<Action>();
	this.id = id;
	this.name = name;
	this.actions = actions;
	this.trigger = trigger;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getId() {
	return id;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

    public LinkedList<Trigger> getTriggers() {
	return trigger;
    }

    public void setTrigger(LinkedList<Trigger> trigger) {
	this.trigger = trigger;
    }

    public LinkedList<Action> getActions() {
	return actions;
    }

    public void setActions(LinkedList<Action> actions) {
	this.actions = actions;
    }

}
