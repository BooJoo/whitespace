package de.fuberlin.whitespace.regelbau.logic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ListItemValueContainer;

/**
 * Ein TriggerVocabulary repräsentiert einen {@link Trigger} zusammen
 * mit einer Menge von möglichen Parametern und den entsprechenden
 * Informationen zur Darstellung in der GUI.
 *
 */
public class TriggerVocabulary {

    /**
     * Instanzen (pro Trigger-Id darf nur eine Instanz existieren)
     */
    private static Map<String, TriggerVocabulary> _instances = new HashMap<String, TriggerVocabulary>();

    /**
     * Id des assoziierten {@link Trigger}s (entspricht dem Attribut `id` der &lt;trigger&gt;-Elemente in ruledata.xml)
     */
    protected String triggerId;

    /**
     * der in der GUI anzuzeigende String (entspricht dem Attribut `word` der &lt;vocabulatory&gt;-Elemente in trigger-vocabulatories.xml)
     */
    private String word;

    private String displayName;

    /**
     * Argumente des Triggers zusammen mit möglichen Operatoren, Werten und Einheiten
     */
    private List<ArgumentData> arguments;

    private TriggerVocabulary (String triggerId) {
	this.triggerId = triggerId;
	this.arguments = new ArrayList<ArgumentData>();
    }

    /**
     * Gibt die mit <tt>triggerId</tt> assoziierte Instanz zurück.
     * @param triggerId
     */
    public static TriggerVocabulary get (String triggerId) {

	if (!_instances.containsKey(triggerId)) {
	    _instances.put(triggerId, new TriggerVocabulary(triggerId));
	}

	return _instances.get(triggerId);
    }

    /**
     * Initialisierung der aktuellen Instanz anhand des entsprechenden &lt;vocabulatory&gt;-XML-Knotens
     * aus trigger-vocabulatories.xml 
     * @param node
     */
    public void init (Node node) {

	NodeList argNodes = node.getChildNodes();
	Node currentNode;
	ArgumentData currentArgumentData;

	this.word = node.getAttributes().getNamedItem("word").getTextContent();
	this.displayName = node.getAttributes().getNamedItem("display-name").getTextContent();

	for (int i=0; i < argNodes.getLength(); i++) {

	    currentNode = argNodes.item(i);

	    if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    currentArgumentData = new ArgumentData();
	    
	    currentArgumentData.name = currentNode.getAttributes().getNamedItem("name").getTextContent();
	    currentArgumentData.values = TriggerVocabulary.fetchArgumentData(currentNode, "values", "value");
	    currentArgumentData.operators = TriggerVocabulary.fetchArgumentData(currentNode, "operators", "operator");
	    currentArgumentData.units = TriggerVocabulary.fetchArgumentData(currentNode, "units", "unit");

	    this.arguments.add(currentArgumentData);
	}
    }

    public String getTriggerId () {
	return this.triggerId;
    }

    /**
     * Gibt den Anzeige-String zur Darstellung in der GUI zurück.
     * @return
     */
    public String getWord () {
	return this.word;
    }

    public String getDisplayName () {
	return this.displayName;
    }

    public List<ArgumentData> getArgumentData () {
	return this.arguments;
    }

    private static List<String> fetchArgumentData(Node node, String rootNodeName, String valueNodeName) {

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

    @Override
    public String toString() {
	return this.displayName;
    }

    @Override
    public int hashCode () {
	return this.triggerId.hashCode();
    }

    @Override
    public boolean equals (Object o) {
	return (o instanceof TriggerVocabulary) && ((TriggerVocabulary) o).triggerId.equals(this.triggerId);
    }

    /**
     * Eine ArgumentData-Instanz ist eine Menge von möglichen
     * Operatoren, Werten und Einheiten für einen bestimmten
     * Parameter eines Triggers.
     *
     */
    public static class ArgumentData {

	private static final HashMap<String, String> operatorDisplayStrings = new HashMap<String, String>() {/**
	 * 
	 */
	    private static final long serialVersionUID = 4294961145814533660L;

	    {
		put("gt",">");
		put("eq","=");
		put("lt","<");
	    }};

	    /**
	     * Name des Arguments
	     */
	    private String name;

	    private List<String> values;
	    private List<String> operators;
	    private List<String> units;

	    /**
	     * ausgewählter Wert
	     */
	    private Integer selectedValue;

	    /**
	     * ausgewählter Operator
	     */
	    private Integer selectedOperator;

	    /**
	     * ausgewählte Einheit
	     */
	    private Integer selectedUnit;

	    private ArgumentData () {
		this.values = new ArrayList<String>();
		this.operators = new ArrayList<String>();
		this.units = new ArrayList<String>();
		this.cleanSelection();
	    }

	    /**
	     * Wert <tt>value</tt> als "ausgewählt" markieren
	     * @param value
	     */
	    public void selectValue (String value) {
		this.selectedValue = this.values.indexOf(value);
	    }
	    
	    public void selectValueByContainer (ListItemValueContainer value) {
		if (value.parentDataObject == this) {
		    this.selectValue((String) value.value);
		}
	    }

	    /**
	     * Operator <tt>operator</tt> als "ausgewählt" markieren
	     * @param value
	     */
	    public void selectOperator (String operator) {
		this.selectedOperator = this.operators.indexOf(operator);
	    }

	    /**
	     * Den zum Anzeige-String <tt>displayString</tt> gehörenden
	     * Operator als "ausgewählt" markieren.
	     * @param displayString
	     */
	    public void selectOperatorByDisplayString (String displayString) {

		for (String op : ArgumentData.operatorDisplayStrings.keySet()) {
		    if (ArgumentData.operatorDisplayStrings.get(op).equals(displayString)) {
			this.selectedOperator = this.operators.indexOf(op);
			return;
		    }
		}

	    }

	    /**
	     * Einheit <tt>unit</tt> als ausgewählt markieren.
	     * @param unit
	     */
	    public void selectUnit (String unit) {
		this.selectedUnit = this.units.indexOf(unit);
	    }

	    /**
	     * Eventuelle Auswahl-Informationen zurücksetzen.
	     */
	    public void cleanSelection() {
		this.selectedValue = -1;
		this.selectedOperator = -1;
		this.selectedUnit = -1;
	    }

	    /**
	     * Gibt den ausgewählten Wert zurück.
	     */
	    public String getSelectedValue () {
		return this.selectedValue >= 0 ? this.values.get(this.selectedValue) : null;
	    }

	    /**
	     * Gibt den ausgewählten Operator zurück.
	     */
	    public String getSelectedOperator () {
		return this.selectedOperator >= 0 ? this.operators.get(this.selectedOperator) : null;
	    }


	    public String getSelectedOperatorDisplaystring() {
		return ArgumentData.operatorDisplayStrings.get(this.getSelectedOperator());
	    }

	    /**
	     * Gibt die ausgewählte Einheit zurück.
	     */
	    public String getSelectedUnit () {
		return this.selectedUnit >= 0 ? this.units.get(this.selectedUnit) : null;
	    }

	    public String getName() {
		return name;
	    }

	    public void setName(String name) {
		this.name = name;
	    }

	    /**
	     * Gibt die möglichen Werte zurück.
	     */
	    public List<String> getValues() {
		return values;
	    }
	    
	    /**
	     * Gibt die Möglichen Werte als {@link ListItemValueContainer} zurück.
	     * @return
	     */
	    public List<ListItemValueContainer> getValueContainers () {
		List<ListItemValueContainer> result = new ArrayList<ListItemValueContainer>();
		
		for (String value : this.values) {
		    result.add(new ListItemValueContainer(value, this));
		}
		
		return result;
	    }

	    /**
	     * Gibt die möglichen Operatoren zurück.
	     */
	    public List<String> getOperators() {
		return operators;
	    }

	    /**
	     * Gibt die Display-Strings der möglichen Operatoren zurück.
	     */
	    public List<String> getOperatorDisplayStrings () {

		List<String> result = new ArrayList<String>();

		for (String op :this.operators) {
		    result.add(ArgumentData.operatorDisplayStrings.get(op));
		}

		return result;
	    }

	    /**
	     * Gibt die möglichen Einheiten zurück.
	     */
	    public List<String> getUnits() {
		return units;
	    }

	    /**
	     * Gibt die Anzahl der möglichen Werte zurück.
	     */
	    public int numValues () {
		return this.values.size();
	    }

	    /**
	     * Gibt die Anzahl der möglichen Operatoren zurück.
	     */
	    public int numOperators () {
		return this.operators.size();
	    }

	    /**
	     * Gibt die anzahl der möglichen Einheiten zurück.
	     */
	    public int numUnits () {
		return this.units.size();
	    }
    }

    /**
     * Diese Klasse dient nur als Container zur Verwendung
     * in der Android-ListView-Komponente.
     *
     */
    public static class ListItemValueContainer {

	private Object value;
	private ArgumentData parentDataObject;

	public ListItemValueContainer(Object value, ArgumentData argumentData) {
	    this.value = value;
	    this.parentDataObject = argumentData;
	}

	@Override
	public String toString() {

	    String label = value.toString();

	    if (parentDataObject.getSelectedUnit() != null) {
		label += " " + parentDataObject.getSelectedUnit();
	    } else if (parentDataObject.numUnits() == 1) {
		label += " " + parentDataObject.getUnits().get(0);
	    }

	    return label;
	}	
    }
}
