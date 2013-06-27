package de.fuberlin.whitespace.regelbau.logic.data;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import de.fuberlin.whitespace.regelbau.logic.Action;
import de.fuberlin.whitespace.regelbau.logic.Trigger;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ActionOption;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ArgumentData;

/**
 * Der DataLoader ist für das deserialisieren der
 * Informationen in den folgenden XML-Dateien verantwortlich:
 * <ul>
 * 	<li>ruledata.xml
 * 	<li>action-vocabulatories.xml
 * 	<li>trigger-vocabulatories.xml
 * </ul>
 *
 */
public class DataLoader {

    private static boolean initialized = false;

    /**
     * Zuordnung: Action-Id -> implementierende Klasse
     */
    private static Map<String, Class<? extends Action>> actions = new HashMap<String, Class<? extends Action>>();

    /**
     * Zuordnung: Trigger-Id -> implementierende Klasse
     */
    private static Map<String, Class<? extends Trigger>> triggers = new HashMap<String, Class<? extends Trigger>>();

    /**
     * Zuordnung: Trigger-Id -> (Argumentname, Argumenttyp)*
     */
    private static Map<String, Map<String, String>> triggerArguments = new HashMap<String, Map<String, String>>();
    
    /**
     * Zuordnung: Trigger-Id -> Actions (in Form ihrer Id), für die dieser Trigger gültig ist
     */
    private static Map<String, List<String>> mappings = new HashMap<String, List<String>>();

    /**
     * Gruppierungen der {@link Trigger} entsprechend &lt;trigger-groups&gt; in ruledata.xml
     */
    private static List<TriggerGroup> triggerGroups = new ArrayList<TriggerGroup>();

    /**
     * Sammlung der {@link ActionVocabulary}s gemäß action-vocabularies.xml
     */
    private static List<ActionVocabulary> actionVocabularies = new ArrayList<ActionVocabulary>();

    public DataLoader (Context ctx) throws SAXException, IOException, ParserConfigurationException, DOMException, ClassNotFoundException {

	if (!DataLoader.initialized) {

	    DataLoader.loadRuleData(ctx);
	    DataLoader.loadVocabularies(ctx);

	    DataLoader.initialized = true;
	}
    }

    public List<ActionVocabulary> getAllActionVocabularies () {

	return DataLoader.actionVocabularies;
    }
    

    public ActionVocabulary getActionVocabulatoryByAction (Action action) {
	
	List<ActionOption> options;
	Map<String, String> optionArgValues;
	Object actionArgValue;
	
	// Dummy-Instanz zur Suche in Liste erzeugen
	ActionVocabulary vocab = ActionVocabulary.buildDummy(action.getId());
	
	if (!DataLoader.actionVocabularies.contains(vocab)) {
	    return null;
	}
	
	// Dummy-Instanz durch gesuchte Instanz ersetzen
	vocab = DataLoader.actionVocabularies.get(DataLoader.actionVocabularies.indexOf(vocab));
	
	options = vocab.getOptions();
	
	// Prüfen, welche ActionOptions zu den Parametern der Action korrespondieren,
	// und entsprechende ActionOptions als selected() markieren.
	for (ActionOption opt : options) {
	    
	    optionArgValues = opt.getArgValues();
	    
	    boolean argSetEqual = true;
	    for (String name : optionArgValues.keySet()) {
		
		if (null == (actionArgValue = action.getParam(name))
			|| !((String) actionArgValue).equals(optionArgValues.get(name))) {
		 
		    argSetEqual = false;
		    break;
		}
	    }
	    
	    if (argSetEqual) {
		opt.select();
	    }
	}
	
	return vocab;
    }
    
    public TriggerVocabulary getTriggerVocabularyByTrigger (Trigger trigger) {
	
	TriggerVocabulary vocab;
	List<ArgumentData> args;
	Trigger.Param triggerParam;
	
	vocab = TriggerVocabulary.get(trigger.getId());
	args = vocab.getArgumentData();
	
	for (ArgumentData arg : args) {
	    
	    if (null != (triggerParam = trigger.getParam(arg.getName()))
		    && arg.getValues().contains(triggerParam.value())) {
		
		arg.selectValue(triggerParam.value());
		arg.selectOperator(triggerParam.getOperatorString());
		arg.selectUnit(triggerParam.unit());
	    }
	}
	
	return vocab;
    }

    /**
     * Gibt die in {@link TriggerGroup}s (&lt;trigger-groups&gt; in ruledata.xml) gruppierten {@link TriggerVocabulatory}s
     * zurück, die entsprechend der Regeln in &lt;action-trigger-mappings&gt; in ruledata.xml nach der
     * Auswahl von <tt>currentActionVocabulary</tt> in Frage kommen.
     * @param currentActionVocabulary
     */
    public List<TriggerGroup> getTriggerGroupsByActionVocabulary(ActionVocabulary currentActionVocabulary) {

	List<TriggerGroup> result = new ArrayList<TriggerGroup>();

	TriggerGroup currentGroup;
	List<TriggerVocabulary> currentGroupValues;

	for (int i=0; i < DataLoader.triggerGroups.size(); i++) {

	    currentGroup = (TriggerGroup) DataLoader.triggerGroups.get(i).clone();
	    currentGroupValues = currentGroup.getVocabularies();

	    for (TriggerVocabulary opt : currentGroupValues) {
		if (!DataLoader.mappings.containsKey(opt.getTriggerId())
			|| !DataLoader.mappings.get(opt.getTriggerId()).contains(currentActionVocabulary.getActionId())) {

		    currentGroup.remove(opt.triggerId);
		}
	    }

	    if (currentGroup.size() > 0) {
		result.add(currentGroup);
	    }
	}

	return result;
    }

    /**
     * Erzeugt und initialisiert eine gültige {@link Action}-Instanz der in
     * <tt>vocab</tt> gespeicherten Auswahloptionen.
     * @param vocab
     */
    public static Action instantiateAction (ActionVocabulary vocab) {
	
	Action result = null;

	try {
	    
	    result =  DataLoader.actions.get(vocab.getActionId()).newInstance();
	    result.setId(vocab.getActionId());
	    result.setStringRepresentation(vocab.getSelectedActionString() + " " + vocab.getSelectedActionOptionsString());
	    
	    for (ActionOption opt : vocab.getOptions()) {
		Map<String, String> argValues = opt.getArgValues();

		for (String name : argValues.keySet()) {
		    result.setParam(name, argValues.get(name));
		}
	    }

	} catch (Throwable t) {
	    throw new RuntimeException(t);
	}

	return result;
    }

    /**
     * Erzeugt und initialisiert eine gültige {@link Trigger}-Instanz der in
     * <tt>vocab</tt> gespeicherten Auswahloptionen.
     * @param vocab
     */
    public static Trigger instantiateTrigger (TriggerVocabulary vocab) {
	
	Trigger result = null;

	try {

	    result =  DataLoader.triggers.get(vocab.getTriggerId()).newInstance();
	    result.setId(vocab.getTriggerId());
	    result.setStringRepresentation(vocab.getSelectedTriggerString());

	    for (ArgumentData arg : vocab.getArgumentData()) {
		
		Trigger.Param param = new Trigger.Param(
			arg.getName(),
			arg.getSelectedValue(),
			DataLoader.triggerArguments.get(vocab.getTriggerId()).get(arg.getName()));
		
		if (arg.getSelectedOperator() != null) {
		    param.setOperator(arg.getSelectedOperator());
		}
		
		if (arg.getSelectedUnit() != null) {
		    param.setUnit(arg.getSelectedUnit());
		}

		result.setParam(param.name(), param);
	    }
	} catch (Throwable t) {
	    throw new RuntimeException(t);
	}

	return result;
    }

    private static void loadVocabularies (Context ctx) throws SAXException, IOException, ParserConfigurationException {

	Document doc;
	NodeList vocabularies;
	String currentTriggerId;

	// action vocabularies

	doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ctx.getAssets().open("action-vocabularies.xml"));
	vocabularies = doc.getElementsByTagName("vocabulary");

	for (int i=0; i < vocabularies.getLength(); i++) {
	    DataLoader.actionVocabularies.add(new ActionVocabulary(vocabularies.item(i)));
	}

	Collections.sort(DataLoader.actionVocabularies);

	// trigger vocabularies

	doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ctx.getAssets().open("trigger-vocabularies.xml"));
	vocabularies = doc.getElementsByTagName("vocabulary");

	for (int i=0; i < vocabularies.getLength(); i++) {

	    currentTriggerId = vocabularies.item(i).getAttributes().getNamedItem("trigger-id").getTextContent();

	    for (TriggerGroup group : DataLoader.triggerGroups) {

		if (group.containsTriggerId(currentTriggerId)) {
		    group.get(currentTriggerId).init(vocabularies.item(i));
		    break;
		}
	    }
	}
    }

    @SuppressWarnings("unchecked")
    private static void loadRuleData(Context ctx) throws SAXException, IOException, ParserConfigurationException, DOMException, ClassNotFoundException {

	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ctx.getAssets().open("ruledata.xml"));

	NodeList actions = doc.getElementsByTagName("actions").item(0).getChildNodes();
	NodeList triggers = doc.getElementsByTagName("triggers").item(0).getChildNodes();
	NodeList mappings = doc.getElementsByTagName("action-trigger-mappings").item(0).getChildNodes();
	NodeList triggerGroups = doc.getElementsByTagName("trigger-groups").item(0).getChildNodes();

	Node currentNode;
	NamedNodeMap attributes;

	// Actions laden
	for (int i=0; i < actions.getLength(); i++) {
	    currentNode = actions.item(i);

	    if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    attributes = currentNode.getAttributes();

	    DataLoader.actions.put(
		    attributes.getNamedItem("id").getTextContent(), 
		    (Class<? extends Action>) Class.forName(attributes.getNamedItem("classname").getTextContent())
		    );
	}

	// Trigger laden
	for (int i=0; i < triggers.getLength(); i++) {
	    
	    currentNode = triggers.item(i);

	    if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    attributes = currentNode.getAttributes();
	    
	    String id = attributes.getNamedItem("id").getTextContent();

	    DataLoader.triggers.put(
		    id,
		    (Class<? extends Trigger>) Class.forName(attributes.getNamedItem("classname").getTextContent())
		    );
	    
	    DataLoader.triggerArguments.put(id, new HashMap<String, String>());
	    
	    NodeList args = currentNode.getChildNodes();
	    
	    for (int j=0; j < args.getLength(); j++) {
		
		if (args.item(j).getNodeType() != Node.ELEMENT_NODE) {
		    continue;
		}
		
		DataLoader.triggerArguments.get(id).put(
			args.item(j).getAttributes().getNamedItem("name").getTextContent(),
			args.item(j).getAttributes().getNamedItem("type").getTextContent());
	    }
	}

	// Invertiertes Mapping: Trigger -> Actions, die diesen Trigger ermöglichen
	for (int i=0; i < mappings.getLength(); i++) {

	    currentNode = mappings.item(i);

	    if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    attributes = currentNode.getAttributes();

	    String currentTrigger;
	    String currentAction = attributes.getNamedItem("action").getTextContent();
	    NodeList currentTriggerNodes = currentNode.getChildNodes();

	    for (int j=0; j < currentTriggerNodes.getLength(); j++) {

		if (currentTriggerNodes.item(j).getNodeType() != Node.ELEMENT_NODE) {
		    continue;
		}

		currentTrigger = currentTriggerNodes.item(j).getTextContent();

		if (!DataLoader.mappings.containsKey(currentTrigger)) {
		    DataLoader.mappings.put(currentTrigger, new ArrayList<String>());
		}

		DataLoader.mappings.get(currentTrigger).add(currentAction);
	    } 
	}

	// Trigger Groups laden
	for (int i=0; i < triggerGroups.getLength(); i++) {

	    TriggerGroup currentGroup;
	    NodeList currentMemberNodes;

	    currentNode = triggerGroups.item(i);

	    if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    attributes = currentNode.getAttributes();

	    currentGroup = new TriggerGroup(
		    attributes.getNamedItem("id").getTextContent(), 
		    attributes.getNamedItem("word").getTextContent());

	    currentMemberNodes = currentNode.getChildNodes();

	    for (int j=0; j < currentMemberNodes.getLength(); j++) {

		if (currentMemberNodes.item(j).getNodeType() != Node.ELEMENT_NODE) {
		    continue;
		}

		currentGroup.put(TriggerVocabulary.get(currentMemberNodes.item(j).getTextContent()));
	    }

	    DataLoader.triggerGroups.add(currentGroup);
	}
    }
}
