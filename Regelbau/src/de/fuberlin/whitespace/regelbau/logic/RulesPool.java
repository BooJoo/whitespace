package de.fuberlin.whitespace.regelbau.logic;

import java.util.LinkedList;

import android.content.Context;

import de.fuberlin.whitespace.regelbau.logic.SQLDB.SQLDataBase;


/**
 * Speichert alle erzeugten Regeln und Führt Aktionen Aus wenn Auslöser whar werden
 * @author Stefan
 *
 */
public  class RulesPool {
	
	private static SQLDataBase DB;
	/** 
	 * liste der Regln 
	 */
	 static public LinkedList<Rule> rules;
	
	
	public RulesPool(Context context)
	{
		rules = new LinkedList<Rule>();
		DB = new SQLDataBase(context);
		
		//Regeln aus der Datenbank holen
		LinkedList<byte[] > DBRulrList = DB.getAllEntries();
		for (byte[] rule : DBRulrList) {
			Rule newRule=(Rule)DB.deserialize(rule);
			//triger wieder anmelden bei Proxyclient
		/*	for (Trigger t : newRule.trigger) {
				t.subscribe();
			}
		
			 */
			//Absturz durch Nullpointer Exception
			rules.add(newRule);
		}
		//rules.removeLast();
	} 
	
	/**
	* hinzufügen einer Neuen Regel mit Triggern und Aktionen
	* @param actions
	* @param trigger
	* @throws Exception wir geworfen bei Aktionen =0 oder Trigger =0
	*/
	public static void AddRule(String name,LinkedList<Action> actions, LinkedList<Trigger> trigger ) throws Exception
	{
		if(actions.isEmpty())
			throw new Exception("Keine Aktion vorhanden für die Ragel.");
		if(trigger.isEmpty())
			throw new Exception("Keine Trigger vorhanden für die Ragel.");
		Rule newRule=new Rule( name,actions,trigger);
		rules.add(newRule);
		DB.AddToDB(DB.serialize(newRule));
		
	}
	
	
	public void updateDB()
	{
		DB.reset();
		LinkedList<byte[] > brules = new LinkedList<byte[]>();
		for (Rule r : rules) {
			brules.add(DB.serialize(r));
		}
		DB.Update(brules);
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
				if(trig==t)//Wenn wir den Trigger gefunden haben
				{
					//Überprüen ob alle Trigger dieser Regel wahr sind
					//und fuehrt ggf. Aktionen dieser Regel aus
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