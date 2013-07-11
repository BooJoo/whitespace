package de.fuberlin.whitespace.regelbau.logic.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Ein ActionVocabulary repräsentiert eine {@link Action} zusammen
 * mit einer Menge von möglichen Parametern und den entsprechenden
 * Informationen zur Darstellung in der GUI.
 *
 */
public class ActionVocabulary implements Comparable<ActionVocabulary> {

    /**
     * Id der assoziierten {@link Action} (entspricht dem Attribut `id` der &lt;action&gt;-Elemente in ruledata.xml)
     */
    private String actionId;

    /**
     * der in der GUI anzuzeigende String (>= 1 Wort) entsprechend dem Attribut `word` der &lt;vocabulatory&gt;-Elemente in action-vocabulatories.xml<br />
     * 
     */
    private String displayString;
    
    /**
     * die in der GUI anzuzeigende Name (== 1 Wort) entsprechend dem Attribut `display-name` der &lt;vocabulatory&gt;-Elemente in action-vocabulatories.xml
     */
    private String displayName;

    /**
     * Signalisiert, ob assoziierte {@link Action} eine Mehrfachauswahl von Argumenten ermöglicht.
     */
    private Boolean multipleChoice;

    /**
     * die möglichen Argumente
     */
    private List<ActionOption> options;
    
    protected ActionVocabulary (Node domNode) {

	NamedNodeMap vocabularyAttributes = domNode.getAttributes();
	NamedNodeMap optionAttributes;
	NodeList options = domNode.getChildNodes();
	NodeList optionArgs;
	Node currentOptionData;
	ActionOption currentOption;
	
	String name, content;
	ArgumentValue currentValue;
	
	this.actionId = vocabularyAttributes.getNamedItem("action-id").getTextContent();
	this.displayString = vocabularyAttributes.getNamedItem("display-string").getTextContent();
	this.displayName = vocabularyAttributes.getNamedItem("display-name").getTextContent();;
	this.multipleChoice = "true".equalsIgnoreCase(vocabularyAttributes.getNamedItem("multiple-choices").getTextContent());

	this.options = new ArrayList<ActionOption>();

	for (int i=0; i < options.getLength(); i++) {

	    currentOptionData = options.item(i);

	    if (currentOptionData.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    optionAttributes = currentOptionData.getAttributes();
	    optionArgs = currentOptionData.getChildNodes();

	    currentOption = new ActionOption(optionAttributes.getNamedItem("word").getTextContent(), this);

	    for (int j=0; j < optionArgs.getLength(); j++) {

		if (optionArgs.item(j).getNodeType() != Node.ELEMENT_NODE) {
		    continue;
		}
		
		name = optionArgs.item(j).getAttributes().getNamedItem("name").getTextContent();
		content = optionArgs.item(j).getTextContent();
		
		currentValue = new ArgumentValue(content);
		
		if (null != optionArgs.item(j).getAttributes().getNamedItem("editable")) {
		    currentValue.editable = !"false".equalsIgnoreCase(optionArgs.item(j).getAttributes().getNamedItem("editable").getTextContent());
		}
		
		if (null != optionArgs.item(j).getAttributes().getNamedItem("edit-display-string")) {
		    currentValue.editingDisplayString = optionArgs.item(j).getAttributes().getNamedItem("edit-display-string").getTextContent();
		}
		
		currentOption.argValues.put(
			name,
			currentValue
			);
	    }

	    this.options.add(currentOption);
	}

	Collections.sort(this.options);
    }

    /**
     * Dieser Konstruktor erzeugt keine gültige Instanz. Er dient nur der Erzeugung
     * eines Dummies zum Suchen in Collections;
     * @param actionId
     */
    private ActionVocabulary(String actionId) {
	this.actionId = actionId;
    }

    /**
     * Gibt die Id der assoziierten {@link Action} (entsprechend dem Attribut `id` der &lt;action&gt;-Elemente in ruledata.xml)
     * zurück.
     */
    public String getActionId() {
	return actionId;
    }

    /**
     * Gibt den in der GUI anzuzeigenden String (>= 1 Wort) zurück.
     */
    public String getDisplayString() {
	return displayString;
    }
    
    /**
     * Gibt das in der GUI anzuzeigende Wort (== 1 Wort) zurück.
     * @return
     */
    public String getDisplayName () {
	return displayName;
    }

    /**
     * Gibt die möglichen Argumente zurück.
     */
    public List<ActionOption> getOptions() {
	return options;
    }

    /**
     * Gibt <tt>true</tt> zurück, falls die assoziierte {@link Action}
     * Mehrfachauswahl von Argumenten ermöglicht. (<tt>false</tt> sonst)
     */
    public boolean isMultipleChoice () {
	return this.multipleChoice;
    }
    
    @Override
    public int hashCode () {
	return this.actionId.hashCode();
    }

    @Override
    public boolean equals (Object other) {
	return (other instanceof ActionVocabulary) && this.actionId.equals(((ActionVocabulary) other).actionId);
    }

    @Override
    public int compareTo (ActionVocabulary other) {
	return this.displayString.compareTo(other.displayString);
    }

    @Override
    public String toString() {
	return this.displayString;
    }
    
    /**
     * Diese Methode erzeugt eine Dummy-Instanz, anhand der in
     * Collections gesucht werden kann.
     * @param actionId
     */
    public static ActionVocabulary buildDummy (String actionId) {
	return new ActionVocabulary(actionId);
    }
    
    public String getSelectedActionString() {
	return this.displayName;
    }

    public String getSelectedActionOptionsString (){
	
	List<ActionOption> activeOptions = ActionOption.getSelection(this);
	int count = Math.min(3, activeOptions.size());
	String label = activeOptions.get(0).getWord();

	for (int i = 1; i < count; i++) {

	    if (i == count - 1) {
		label += " und ";
	    } else {
		label += ", ";
	    }

	    label += activeOptions.get(i);
	}
	
	return label;
    }
    
    /**
     * Eine ActionOption-Instanz repräsentiert eine konkrete
     * Belegung der Argumente einer {@link Action} zusammen
     * mit Zusatzinformationen zur Darstellung in der GUI.
     *
     */
    public static class ActionOption implements Comparable<ActionOption> {

	/**
	 * der in der GUI anzuzeigende String
	 */
	private String word;

	private Map<String, ArgumentValue> argValues;

	private boolean selected;

	private ActionVocabulary parent;

	private ActionOption (String word, ActionVocabulary parent) {
	    this.word = word;
	    this.selected = false;
	    this.parent = parent;
	    this.argValues = new HashMap<String, ArgumentValue>();
	}

	/**
	 * Gibt den in der GUI anzuzeigenden String zurück.
	 */
	public String getWord () {
	    return this.word;
	}
	
	public ArgumentValue getArgValue (String name) {
	    return this.argValues.get(name);
	}

	public Map<String, ArgumentValue> getArgValues() {
	    return argValues;
	}

	/**
	 * Signalisiert die Auswahl dieser Aktion als Parameter
	 * für die ausgewählte {@link ActionVocabulary}
	 */
	public void select() {
	    if (!this.parent.isMultipleChoice()) {
		ActionOption.clearSelection(this.parent);
	    }
	    this.selected = true;
	}

	/**
	 * Gibt die ausgewählten {@link ActionOption}s zurück.
	 * @param vocab
	 */
	public static List<ActionOption> getSelection(ActionVocabulary vocab) {
	    List<ActionOption> result = new ArrayList<ActionOption>();

	    for (ActionOption opt : vocab.options) {
		if (opt.selected) {
		    result.add(opt);
		}
	    }

	    return result;
	}
	
	public static Map<String,List<ArgumentValue>> getArgumentValues (List<ActionOption> options) {
	    Map<String, List<ArgumentValue>> result = new HashMap<String, List<ArgumentValue>>();
	    
	    for (ActionOption opt : options) {
		for (String valKey : opt.argValues.keySet()) {
		    if (!result.containsKey(valKey)) {
			result.put(valKey, new ArrayList<ArgumentValue>());
		    }
		    
		    result.get(valKey).add(opt.argValues.get(valKey));
		}
	    }
	    
	    return result;
	}

	/**
	 * Setzt eine eventuell vorangegangene Auswahl zurück
	 * @param vocab
	 */
	public static void clearSelection (ActionVocabulary vocab) {
	    for (ActionOption opt : vocab.options) {
		opt.selected = false;
	    }
	}

	/**
	 * Gibt die Anzahl der ausgewählten Optionen zurück.
	 * @param vocab
	 */
	public static int numSelectedItems(ActionVocabulary vocab) {
	    int result = 0;

	    for (ActionOption opt : vocab.options) {
		if (opt.selected) {
		    result++;
		}
	    }

	    return result;
	}

	@Override
	public int compareTo (ActionOption other) {
	    return this.word.compareTo(other.word);
	}

	@Override
	public String toString() {
	    return this.word;
	}
    }
    
    public class ArgumentValue {
	
	private String initalValue;
	private String editedValue;
	
	private String editingDisplayString;
	
	private boolean editable;
	
	ArgumentValue (String value, boolean editable) {
	    this.initalValue = value;
	    this.editedValue = null;
	    this.editingDisplayString = null;
	    this.editable = editable;
	}
	
	ArgumentValue (String value) {
	    this(value, false);
	}
	
	public boolean isEditable () {
	    return this.editable;
	}
	
	public String getInitialValue () {
	    return this.initalValue;
	}
	
	public String getCurrentValue () {
	    return this.editedValue != null ? this.editedValue : this.initalValue;
	}
	
	public void reset () {
	    this.editedValue = null;
	}
	
	public String getEditingDisplayString () {
	    return this.editingDisplayString;
	}

	public void setEditedValue(String value) {
	    this.editedValue = value;
	}
    }
}
