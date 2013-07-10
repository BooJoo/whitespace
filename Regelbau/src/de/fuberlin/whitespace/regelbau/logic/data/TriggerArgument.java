package de.fuberlin.whitespace.regelbau.logic.data;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ListItemValueContainer;

public class TriggerArgument {

    protected static final HashMap<String, String> operatorDisplayStrings = new HashMap<String, String>() {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4294961145814533660L;

	{
	    put("gt",">");
	    put("eq","=");
	    put("lt","<");
	}
    };
    /**
     * Name des Arguments
     */
    protected String name;
    
    private AbstractArgumentSelector selector;

    protected String valueType;
    
    private String selectedValue;
    private String selectedOperator;
    private String selectedUnit;
    
    protected TriggerArgument (Node node, Class<? extends AbstractArgumentSelector> clazz) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	this.selectedValue = null;
	this.selectedOperator = null;
	this.selectedUnit = null;
	this.init(node);
	this.selector = (AbstractArgumentSelector) clazz.getDeclaredConstructor(TriggerArgument.class).newInstance(this);
    }
    
    protected void init (Node node) {
	this.name = node.getAttributes().getNamedItem("name").getTextContent();
	
	// 
	NodeList children = node.getChildNodes();
	for (int i=0; i < children.getLength(); i++) {
	    if (children.item(i).getNodeName().equals("values")) {
		Node typeAttr;
		if (null != (typeAttr = children.item(i).getAttributes().getNamedItem("type"))) {
		    this.valueType = typeAttr.getTextContent();
		}
		break;
	    }
	}
    }

    /**
     * Wert <tt>value</tt> als "ausgewählt" markieren
     * @param value
     */
    public void selectValue(String value) {
	if (value != null) {
	    this.selectedValue = value;
	}
    }

    /**
     * Gibt den ausgewählten Wert formatiert zur Anzeige
     * in der GUI zurück.
     * @return
     */
    public String getSelectedValueString() {
	if ("Timespan".equalsIgnoreCase(this.valueType)) {
	    
	    String result = "";
	    
	    Integer minutes = Integer.valueOf(this.selectedValue);
	    Integer hours = minutes / 60;
	    minutes = minutes - hours * 60;
	    
	    if (hours > 0) {
		result += hours + (hours > 0 && minutes > 0 ? "h " : " Stunden");
	    }
	    
	    if (minutes > 0) {
		result += minutes + (hours > 0 && minutes > 0 ? "m " : " Minuten");
	    }

	    return result;
	    
	} else if ("Timestamp".equalsIgnoreCase(this.valueType)) {
	    
	    String result = "";
	    
	    Integer minutes = Integer.valueOf(this.selectedValue);
	    Integer hours = minutes / 60;
	    minutes = minutes - hours * 60;
	    
	    result += (hours < 10 ? "0" : "") + hours;
	    result += ":";
	    result += (minutes < 10 ? "0" : "") + minutes;
	    
	    return result;
	    
	} else {
	    
	    return this.selectedValue != null ? this.selectedValue : "";
	}
    }

    /**
     * Gibt den {@link AbstractArgumentSelector} zurück, der für die
     * Eingabe der Werte dieses Arguments zuständig ist.
     * @return
     */
    public AbstractArgumentSelector getSelector() {
        return selector;
    }

    public void selectValue(ListItemValueContainer value) {
	if (value != null && value.parentDataObject == this) {
	    this.selectValue((String) value.value);
	}
    }

    /**
     * Operator <tt>operator</tt> als "ausgewählt" markieren
     * @param value
     */
    public void selectOperator(String operator) {
	if (operator != null) {
	    this.selectedOperator = operator;
	}
    }

    /**
     * Den zum Anzeige-String <tt>displayString</tt> gehörenden
     * Operator als "ausgewählt" markieren.
     * @param displayString
     */
    public void selectOperatorByDisplayString(String displayString) {
	
	if (displayString == null) {
	    return;
	}

	for (String op : TriggerArgument.operatorDisplayStrings.keySet()) {
	    if (TriggerArgument.operatorDisplayStrings.get(op).equals(displayString)) {
		this.selectedOperator = op;
		return;
	    }
	}

    }

    /**
     * Einheit <tt>unit</tt> als ausgewählt markieren.
     * @param unit
     */
    public void selectUnit(String unit) {
	if (unit != null) {
	    this.selectedUnit = unit;
	}
    }

    /**
     * Eventuelle Auswahl-Informationen zurücksetzen.
     */
    public void resetSelection() {
	this.selectedValue = null;
	this.selectedOperator = null;
	this.selectedUnit = null;
    }

    /**
     * Gibt den ausgewählten Wert zurück.
     */
    public String getSelectedValue() {
	return this.selectedValue;
    }

    /**
     * Gibt den ausgewählten Operator zurück.
     */
    public String getSelectedOperator() {
	return this.selectedOperator;
    }

    public String getSelectedOperatorDisplaystring() {
	return TriggerArgument.operatorDisplayStrings.get(this.getSelectedOperator());
    }

    /**
     * Gibt den Typ des Arguments zurück.
     * (gemäß dem in trigger-vocabularies.xml im <tt>&lt;values&lt;</tt>-Element dieses Argument definierten <tt>type</tt>-Wert)
     * @return
     */
    public String getType() {
	return valueType;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }


    /**
     * Gibt die ausgewählte Einheit zurück.
     */
    public String getSelectedUnit() {
        return this.selectedUnit;
    }

    /**
     * Erzeugt ein TriggerArgument mit der in trigger-vocabularies.xml definierten
     * Implementierung (Attribut <tt>type</tt> der <tt>&lt;vocabulary&gt;</tt>-Elemente)
     * und dem dort definierten {@link AbstractArgumentSelector} (Attribut <tt>selector</tt> der <tt>&lt;vocabulary&gt;</tt>-Elemente).
     * @param node
     * @return
     * @throws ClassCastException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static TriggerArgument build(Node node) throws ClassCastException, ClassNotFoundException, InstantiationException, InvocationTargetException {
	
	Class<?> argImpl = Class.forName(node.getAttributes().getNamedItem("type").getTextContent());
	Class<?> selectorImpl = Class.forName(node.getAttributes().getNamedItem("selector").getTextContent());
	
	if (!TriggerArgument.class.equals(argImpl) && !TriggerArgument.class.equals(argImpl.getGenericSuperclass())) {
	    throw new ClassCastException();
	}
	
	if (!AbstractArgumentSelector.class.equals(selectorImpl.getGenericSuperclass())) {
	    throw new ClassCastException();
	}
	
	TriggerArgument result = null;
	
	try {
	    result =  (TriggerArgument) argImpl.getDeclaredConstructor(Node.class, Class.class).newInstance(node, selectorImpl);
	} catch (Exception e) {
	    
	    if (e instanceof InstantiationException) {
		throw (InstantiationException) e;
	    } else if (e instanceof InvocationTargetException) {
		throw (InvocationTargetException) e;
	    } else {
		/**
		 * IllegalArgumentException, IllegalAccessException und NoSuchMethodException
		 * sind durch Konstruktor TriggerArgument(Node n, Class<? extends AbstractArgumentSelector> c)
		 * ausgeschlossen.
		 */
	    }
	}
	
	return result;
    }

    protected static List<String> fetchArgumentData(Node node, String rootNodeName, String valueNodeName) {

	List<String> result = new ArrayList<String>();

	NodeList children = node.getChildNodes();

	for (int i=0; i < children.getLength(); i++) {

	    if (children.item(i).getNodeName().equals(rootNodeName)) {

		children = children.item(i).getChildNodes();

		for (int j=0; j < children.getLength(); j++) {
		    if (children.item(j).getNodeType() == Node.ELEMENT_NODE) {
			result.add(children.item(j).getTextContent());
		    }
		}

		break;
	    }
	}

	return result;
    }
}
