package de.fuberlin.whitespace.regelbau.logic.data;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.fuberlin.whitespace.regelbau.logic.Trigger;

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
     * der anzugzeigende Name
     */
    private String displayName;
    
    /**
     * ein optionaler Präfix zur Anzeige vor (Operator + Wert + Einheit)
     */
    private String displayPrefix;

    /**
     * ein optionaler Suffix zur Anzeige nach (Operator + Wert + Einheit)
     */
    private String displaySuffix;

    /**
     * Argumente des Triggers zusammen mit möglichen Operatoren, Werten und Einheiten
     */
    private List<TriggerArgument> arguments;

    private TriggerVocabulary (String triggerId) {
	this.triggerId = triggerId;
	this.arguments = new ArrayList<TriggerArgument>();
	this.displayName = null;
	this.displayPrefix = "";
	this.displaySuffix = "";
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
     * aus trigger-vocabulatories.xml.
     * @param node
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws ClassCastException 
     */
    public void init (Node node) throws ClassCastException, ClassNotFoundException, InstantiationException, InvocationTargetException {

	NodeList argNodes = node.getChildNodes();
	Node currentNode;
	TriggerArgument currentArgumentData;
	
	this.displayName = node.getAttributes().getNamedItem("display-name").getTextContent();
	
	if (null != (currentNode = node.getAttributes().getNamedItem("display-prefix"))) {
	    this.displayPrefix = currentNode.getTextContent();
	}
	
	if (null != (currentNode =  node.getAttributes().getNamedItem("display-suffix"))) {
	    this.displaySuffix = currentNode.getTextContent();
	}

	for (int i=0; i < argNodes.getLength(); i++) {

	    currentNode = argNodes.item(i);

	    if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }
	    
	    currentArgumentData = TriggerArgument.build(currentNode);
	    
	    this.arguments.add(currentArgumentData);
	}
    }

    public String getTriggerId () {
	return this.triggerId;
    }

    /**
     * Gibt den anzuzeigenden Namen zurück.
     * @return
     */
    public String getDisplayName () {
	return this.displayName;
    }
    
    /**
     * Gibt den vor (Operator + Wert + Einheit) anzuzeigenden String zurück.
     * @return
     */
    public String getDisplayPrefix () {
	return this.displayPrefix;
    }
    
    /**
     * Gibt den nach (Operator + Wert + Einheit) anzuzeigenden String zurück.
     * @return
     */
    public String getDisplaySuffix () {
	return this.displaySuffix;
    }

    public List<TriggerArgument> getArgumentData () {
	return this.arguments;
    }

    /**
     * Gibt eine vollständige (anzeigbare) String-Repräsentation
     * des gewählten Argumentwertes zurück.
     * @return
     */
    public String getSelectedTriggerString (){

	String label = "";
	
	if (this.getArgumentData().size() > 0) {

	    TriggerArgument arg = this.getArgumentData().get(0);

	    if (arg.getSelectedValue() != null) {
		label += arg.getSelectedValueString();

		if (arg.getSelectedUnit() != null) {
		    label += " " + arg.getSelectedUnit();
		}
		
		if (arg.getSelectedOperator() != null) {
			label = arg.getSelectedOperatorDisplaystring() + " " + label;
		    }
	    }
	}
	
	if (label.length() > 0) {
	    label = this.displayPrefix + " " + label + " " + this.displaySuffix;
	} else {
	    label = this.displayName;
	}

	return label;
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
     * Diese Klasse dient nur als Container zur Verwendung
     * in der Android-ListView-Komponente.
     *
     */
    public static class ListItemValueContainer {

	Object value;
	PreselectedTriggerArgument parentDataObject;

	public ListItemValueContainer(Object value, PreselectedTriggerArgument argumentData) {
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
