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
public class Rule {

	LinkedList<Trigger> trigger;// = new LinkedList<Trigger>();
	LinkedList<Action> actions;// = new LinkedList<Action>();
	
	/**
	 * Konstruktor
	 * @param actions Erwartet Linkedlist von den Aktionen die Ausgeführt werden sollen
	 * @param trigger erwartet Linkedlist von den Triggern für für die Regel
	 */
	public Rule(LinkedList<Action> actions, LinkedList<Trigger> trigger )
	{
		// trigger = new LinkedList<Trigger>();
	//	 actions = new LinkedList<Action>();
		 this.actions = actions;
		 this.trigger = trigger;
	}

	

	public LinkedList<Trigger> getTrigger() {
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
