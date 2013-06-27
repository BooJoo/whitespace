package de.fuberlin.whitespace.regelbau.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;

import de.exlap.DataObject;
import de.exlap.ExlapException;
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
    public boolean aktiviert=false;

    /**
     * <i>Anmerkung:</i> Dieser Konstruktor wird von {@link DataLoader#instantiateTrigger} implizit
     * als existent vorausgesetzt.
     * @see DataLoader#instantiateTrigger(de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary)
     * @param id
     */
    public Trigger () {
	this.params = new HashMap<String, Param>();
    }

    /**
     * Nimmt Parkete Entgegen für die es sich vorher bei ProxyClient Angemeldet hat,
     * Und Entscheidet an denen ob Sich der Status (boolean aktive) veraendert
     * geschieht dieses, so muss RulesPool.TriggerActive(this); aufgerufen werden
     * @param i Platzhalter fuer Parketdifinition
     */
    public abstract void trigger (DataObject dataObject) ;

    public String getId () {
	return id;
    }

    public Param getParam (String name) {
	return params.get(name);
    }

    public void setId (String id) {
	this.id = id;
    }

    public void setParam (String name, Param param) {
	params.put(name, param);
    }
    
    public void setStringRepresentation (String str) {
	this.stringRepresentation = str;
    }

    protected void subscribe (String dataObjectUrl) {
	try {
	    ProxyClient.get().addListener(dataObjectUrl, this);
	} catch (Throwable t) {
	    throw new RuntimeException(t);
	}
    }

    @Override
    public boolean equals (Object o) {
	return (o instanceof Trigger) && this.id.equals(((Trigger) o).id);
    }
    
    @Override
    public String toString () {
	return this.stringRepresentation;
    }

    public static class Param implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006293950820752217L;

	private final String name;

	private final String value;

	private final String type;

	private String unit;

	private String operator;

	private transient Comparator<String> comparator;

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

	public String getOperatorString() {
	    return this.operator;
	}

	public boolean op (String value) {
	    if (this.compareValue == null) {
		return false;
	    }

	    int comparatorValue = this.comparator.compare(value, this.value);

	    return this.compareValue * comparatorValue > 0 || this.compareValue.equals(compareValue);
	}	    

	public boolean op (Object value) {
	    return this.op(value.toString());
	}

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

	public void setUnit (String unit) {
	    this.unit = unit;
	}

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
