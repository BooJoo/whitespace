package de.fuberlin.whitespace.regelbau.logic;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;

import de.exlap.DataObject;

/**
 * Interface fuer die Ausloeser einer Regel
 * @author Stefan Lange
 */
public abstract class Trigger implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3386299757013046952L;
    
    protected String id;

    protected HashMap<String, Object> params;
    
    public Trigger () {
	this.params = new HashMap<String, Object>();
    }
    
    /**
     * gibt an ob der Trigger derzeitig erfüllt ist oder nicht 
     */
    public boolean aktive=false;

    /**
     * Nimmt Parkete Entgegen für die es sich vorher bei ProxyClient Angemeldet hat,
     * Und Entscheidet an denen ob Sich der Status (boolean aktive) veraendert
     * geschieht dieses, so muss RulesPool.TriggerActive(this); aufgerufen werden
     * @param i Platzhalter fuer Parketdifinition
     */
    public abstract void trigger (DataObject dataObject) ;

    public void set (String name, Object value) {
	params.put(name, value);
    }

    @Override
    public boolean equals (Object o) {
	return (o instanceof Trigger) && this.id.equals(((Trigger) o).id);
    }

    public static class Param {
	
	private final String name;
	
	private final String value;
	
	private String unit;
	
	private Comparator<String> comparator;
	
	private Integer compareValue;
	
	public Param (String name, String value, final String type) {
	    this.name = name;
	    this.value = value;
	    this.compareValue = null;
	    this.unit = null;
	    
	    this.comparator = new Comparator<String>(){

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
	
	public Param (String name, String value, String type, String operator) {
	    this(name, value, type);
	    this.setOperator(operator);
	}
	
	public String name () {
	    return name;
	}
	
	public Object value () {
	    return value;
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
    }
}
