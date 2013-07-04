package de.fuberlin.whitespace.regelbau.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;

/**
 * Interface fuer die Ausloeser einer Regel
 * @author Stefan Lange
 */
public abstract class Trigger implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3386299757013046953L;

    private String id;

    private String stringRepresentation;

    private HashMap<String, Param> params;

    /**
     * gibt an ob der Trigger derzeitig erfüllt ist oder nicht 
     */
    private boolean aktiviert;

    private boolean awake;

    private Rule parent;

    private List<String> dataObjectSubscriptions;

    /**
     * <i>Anmerkung:</i> Dieser Konstruktor wird von {@link DataLoader#instantiateTrigger} implizit
     * als existent vorausgesetzt.
     * @see DataLoader#instantiateTrigger(de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary)
     * @param id
     */
    public Trigger () {
	this.params = new HashMap<String, Param>();
	this.dataObjectSubscriptions = new ArrayList<String>();
	this.parent = null;
	this.aktiviert = false;
	this.awake = false;
    }

    protected abstract boolean isFullfilled (DataObject dataObject);

    /**
     * Diese Methode wird nach jeder Ausführung von
     * {@link #wakeUp()} aufgerufen und kann von konkreten
     * Implementierungen genutzt werden um den internen
     * Initialzustand herzustellen oder Datenpakete über
     * {@link #subscribeDataObject(String)} zu abonnieren.
     */
    protected abstract void onWakeUp ();

    /**
     * Diese Methode wird nach jeder Ausführung von
     * {@link #fallAsleep()} aufgerufen und kann von
     * konkreten Implementierungen genutzt werden um
     * ggf. den internen Zustand zurückzusetzen.
     */
    protected void onFallAsleep () {
	
    }

    /**
     * Diese Methode nimmt die Datenobjekte vom {@link ProxyClient}
     * entgegen, entscheidet ob die Bedingung des konkreten Triggers
     * erfüllt ist und signalisiert dies ggf. der zugehörigen Regel
     * über {@link Rule#signalTriggerStateChange(Trigger)}.
     * @param dataObject
     */
    public void trigger (DataObject dataObject) {

	// Um zu vermeiden, dass Trigger ausgelöst werden, die nicht `wach` sind.
	if (!this.isAwake()) {
	    return;
	}

	boolean oldState = this.aktiviert;
	boolean newState = this.isFullfilled(dataObject);

	if (oldState != newState) {
	    this.aktiviert = newState;
	    this.parent.signalTriggerStateChange(this);
	}
    }

    /**
     * Diese Methode signalisiert der Trigger-Instanz, dass
     * die {@link Rule}-Instanz, zu der sie gehört hat,
     * gelöscht wird/wurde.<br />
     * Alle Abonnements von Datenobjekten beim {@link ProxyClient}
     * werden aufgehoben und {@link #onFallAsleep()} aufgerufen.
     */
    public void signalDestruction() {
	
	this.fallAsleep();
	this.parent = null;
	
    }

    /**
     * Über diese Methode können sich konkrete Implementierungen von Trigger
     * für Datenobjekte mit der gegebenen URL anmelden.
     * @param dataObjectUrl
     */
    protected void subscribeDataObject (String dataObjectUrl) {

	if (this.isAwake() && !this.dataObjectSubscriptions.contains(dataObjectUrl)) {
	    try {
		this.parent.getPool().getClient().addListener(dataObjectUrl, this);
		this.dataObjectSubscriptions.add(dataObjectUrl);
	    } catch (Throwable t) {
		throw new RuntimeException(t);
	    }
	}
	
    }
    
    /**
     * Über diese Methode können konkrete Implementierungen von Trigger
     * eine über {@link #subscribeDataObject(String)} erfolgte Anmeldung
     * für Datenobjekte mit der gegebenen URL wieder aufheben.<br /><br />
     * <b>Wichtig:</b> Diese Methode führt Abmeldungen nur aus, wenn die
     * aktuelle Trigger-Instanz schläft (<tt>{@link #isAwake()} == false</tt>).
     * @param dataObjectUrl
     */
    protected void unsubscribeDataObject (String dataObjectUrl) {
	
	if (!this.isAwake() && this.dataObjectSubscriptions.contains(dataObjectUrl)) {
	    try {
		this.parent.getPool().getClient().removeListener(dataObjectUrl, this);
		this.dataObjectSubscriptions.remove(dataObjectUrl);
	    } catch (Throwable t) {
		throw new RuntimeException(t);
	    }
	}
	
    }

    /**
     * Diese Methode weckt die aktuelle Instanz auf und
     * führt {@link #onWakeUp()} aus.
     */
    public void wakeUp () {
	
	this.awake = true;
	this.onWakeUp();
    }

    /**
     * Diese Methode versetzt den aktuellen Trigger
     * in den Schlafzustand, hebt alle über {@link #subscribeDataObject(String)}
     * erfolgten Datenabonnements auf und ruft {@link #onFallAsleep()} auf.
     */
    public void fallAsleep () {
	
	this.awake = false;
	this.aktiviert = false;
	
	for (String dataObjectUrl : this.dataObjectSubscriptions) {
	    this.unsubscribeDataObject(dataObjectUrl);
	}
	
	this.onFallAsleep();
    }

    /**
     * Gibt <tt>true</tt> zurück, falls die Trigger-Instanz
     * im <i>awake</i>-Zustand ist, und <tt>false</tt> sonst
     * zurück.
     */
    public boolean isAwake () {
	return this.awake;
    }

    public String getId () {
	return id;
    }

    public void setId (String id) {
	this.id = id;
    }

    public Param getParam (String name) {
	return params.get(name);
    }

    public void setParam (String name, Param param) {
	params.put(name, param);
    }

    public void removeParam (String name) {
	params.remove(name);
    }

    public void hasParam (String name) {
	params.containsKey(name);
    }

    public List<String> getParamNames() {
	List<String> result = new ArrayList<String>();
	result.addAll(this.params.keySet());
	return result;
    }

    /**
     * Gibt die {@link Rule}-Instanz zurück, zu der dieser
     * Trigger gehört.
     */
    public Rule getParent () {
	return this.parent;
    }

    /**
     * Legt die {@link Rule}-Instanz fest, zu der dieser
     * Trigger gehört.
     * @param rule
     */
    public void setParent(Rule rule) {
	this.parent = rule;
    }

    /**
     * Gibt <tt>true</tt>, falls dieser Trigger
     * aktiviert ist, und <tt>false</tt> sonst zurück.
     * @return
     */
    public Boolean getState() {
	return this.aktiviert;
    }

    /**
     * Legt den String fest, durch den der Trigger im
     * User Interface repräsentiert wird.
     * @param str
     */
    public void setStringRepresentation (String str) {
	this.stringRepresentation = str;
    }

    @Override
    public int hashCode () {
	return this.id.hashCode();
    }

    @Override
    public boolean equals (Object o) {
	return (o instanceof Trigger) && this.id.equals(((Trigger) o).id);
    }

    @Override
    public String toString () {
	return this.stringRepresentation;
    }

    /**
     * Ein Param bündelt neben Name, Wert, Typ und Einheit
     * eines Trigger-Parameters auch die zugehörige Vergleichsoperation
     * zur Auswertung in {@link Trigger#isFullfilled(DataObject)}.
     *
     */
    public static class Param implements Serializable {

	/**
	 * 
	 */
	 private static final long serialVersionUID = 2006293950820752217L;

	 /**
	  * Der Name des Parameters
	  */
	 private final String name;

	 /**
	  * Der Zielwert
	  */
	 private final String value;

	 /**
	  * Der Typ des Parameters
	  */
	 private final String type;

	 /**
	  * Seine Einheit
	  */
	 private String unit;

	 /**
	  * Der ausgewählte Vergleichsoperator
	  */
	 private String operator;

	 /**
	  * Der Comparator implementiert die durch {@link type} 
	  * festgelegte Vergleichsoperation.
	  */
	 private transient Comparator<String> comparator;

	 /**
	  * compareValue ist der Wert, der bei Vergleich
	  * von {@link value} und dem zu prüfenden Wert des
	  * Datenpakets zur Erfüllung des {@link Trigger}s
	  * resultieren muss.
	  */
	 private Integer compareValue;

	 public Param (String name, String value, String type) {
	     this.name = name;
	     this.value = value;
	     this.type = type;
	     this.operator = null;
	     this.compareValue = null;
	     this.unit = null;

	     this.comparator = this.buildComparator();
	 }

	 public Param (String name, String value, String type, String operator) {
	     this(name, value, type);
	     this.setOperator(operator);
	 }

	 public String name () {
	     return name;
	 }

	 public String value () {
	     return value;
	 }

	 public String unit () {
	     return unit;
	 }

	 /**
	  * Gibt eine anzeigbare Repräsentation des
	  * Operators zurück.
	  */
	 public String getOperatorString() {
	     return this.operator;
	 }

	 /**
	  * Gibt <tt>true</tt> zurück, falls <tt>inputValue</tt>
	  * (die String-Repräsentation eines Wertes)
	  * den durch den Wert des Parameters, dessen Typ
	  * und den Operator definierten Vergleich besteht,
	  * <tt>false</tt> andernfalls.
	  * @param inputValue
	  */
	 public boolean op (String inputValue) {
	     if (this.compareValue == null) {
		 return false;
	     }

	     int comparatorValue = this.comparator.compare(inputValue, this.value);
	     
	     return this.compareValue * comparatorValue > 0 || this.compareValue.equals(compareValue);
	 }	    

	 /**
	  * Gibt <tt>true</tt> zurück, falls <tt>inputValue</tt>
	  * den durch den Wert des Parameters, dessen Typ
	  * und den Operator definierten Vergleich besteht,
	  * <tt>false</tt> andernfalls.
	  * @param inputValue
	  */
	 public boolean op (Object inputValue) {
	     return this.op(inputValue.toString());
	 }
	 
	 /**
	  * Legt den Vergleichsoperator fest:
	  * <ul>
	  * 	<li><tt>lt</tt> - kleiner als
	  * 	<li><tt>gt</tt> - größer als
	  * 	<li><tt>eq</tt> - gleich
	  * </ul>
	  * @param operator
	  */
	 public void setOperator (String operator) {

	     this.operator = operator;

	     if (operator.equals("lt")) {
		 this.compareValue = -1;
	     } else if (operator.equals("gt")) {
		 this.compareValue = 1;
	     } else if (operator.equals("eq")) {
		 this.compareValue = 0;
	     } else {
		 throw new RuntimeException("Unknown operator '" + operator + "'.");
	     }
	 }

	 /**
	  * Legt die Einheit des Parameterwertes fest.
	  * @param unit
	  */
	 public void setUnit (String unit) {
	     this.unit = unit;
	 }

	 /**
	  * Erzeugt den Comparator zur Umsetzung des
	  * konkreten Wertevergleichs.
	  */
	 private Comparator<String> buildComparator() {
	     return new Comparator<String>() {

		 @Override
		 public int compare (String a, String b) {

		     int result = 0;

		     if (type.equals("Integer")) {
			 result = Integer.valueOf(a).compareTo(Integer.valueOf(b));
		     } else if (type.equals("Float")) {
			 result = Double.valueOf(a).compareTo(Double.valueOf(b));
		     } else {
			 throw new RuntimeException("Unknown parameter type '" + type + "'.");
		     }

		     return result;
		 }};
	 }

	 private synchronized void readObject (ObjectInputStream s ) throws IOException, ClassNotFoundException {

	     s.defaultReadObject();

	     this.comparator = this.buildComparator();

	     if (this.operator != null) {
		 this.setOperator(this.operator);
	     }
	 }
    }

}
