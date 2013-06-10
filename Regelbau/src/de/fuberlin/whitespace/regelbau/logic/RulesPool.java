package de.fuberlin.whitespace.regelbau.logic;

import java.util.LinkedList;


/**
 * Speichert alle erzeugten Regeln und Führt Aktionen Aus wenn Auslöser whar werden
 * @author Stefan
 *
 */
public  class RulesPool {
	
	/** 
	 * liste der Regln
	 */
	 static LinkedList<Rule> rules = new LinkedList<Rule>();

	 /**
	  * hinzufügen einer Neuen Regel mit Triggern und Aktionen
	  * @param actions
	  * @param trigger
	  * @throws Exception wir geworfen bei Aktionen =0 oder Trigger =0
	  */
	public static void AddRule(LinkedList<Action> actions, LinkedList<Trigger> trigger ) throws Exception
	{
		if(actions.isEmpty())
			throw new Exception("Keine Aktion vorhanden für die Ragel.");
		if(trigger.isEmpty())
			throw new Exception("Keine Trigger vorhanden für die Ragel.");
		
		rules.add(new Rule(actions,trigger));
	}
	
	/**
	 * Wird von Triggern Aufgerufen wenn sie war werden,
	 * führt dann ggf. eine Regel bzw. Aktion aus. 
	 * @param t Trigger : erwartet den Trigger der War geworden ist
	 */
	public static void TriggerCall(Trigger t)
	{
		for ( Rule r : rules) //Alle Regeln Nach dem Trigger durchsuchen
		{
			for ( Trigger trig : r.trigger) 
			{
				if(trig==t)//Wenn wir den Trigger gefungen
				{
					//Überprüen ob alle Trigger dieser Regel War sind
					//und fuert ggf. Aktionen dieser Regel aus
					boolean allTrigger=true; //bleibt True wenn alle Trigger der Regel wahr sind
					
					for ( Trigger trig2 : r.trigger)  
					{
						if(!trig2.aktive)
						{allTrigger=false;}
					}
					
					if(allTrigger)
					for ( Action action : r.actions) 
					{
						action.Do();
					}
					
					break;
				}
			}
		}
		
		
	}

}