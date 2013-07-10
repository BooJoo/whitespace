package de.fuberlin.whitespace.regelbau.logic.data;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ListItemValueContainer;

/**
 * Eine PreselectedTriggerArgument ist ein TriggerArgument,
 * mit einer festen und unveränderlichen (in trigger-vocabularies.xml definierten)
 * Menge von Werten, Operatoren und Einheiten.
 */
public class PreselectedTriggerArgument extends TriggerArgument {

    /**
     * mögliche Werte
     */
    protected List<String> values;
    
    /**
     * mögliche Operatoren
     */
    protected List<String> operators;
    
    /**
     * mögliche Einheiten
     */
    protected List<String> units;

    protected PreselectedTriggerArgument (Node node, Class<? extends AbstractArgumentSelector> clazz) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	super(node, clazz);
	this.init(node);
    }

    @Override
    protected void init (Node node) {
	super.init(node);

	this.values = TriggerArgument.fetchArgumentData(node, "values", "value");
	this.operators = TriggerArgument.fetchArgumentData(node, "operators", "operator");
	this.units = TriggerArgument.fetchArgumentData(node, "units", "unit");

	this.resetSelection();
    }

    @Override
    public void resetSelection() {

	if (this.values.size() > 0) {
	    this.selectValue(this.values.get(0));
	}

	if (this.operators.size() > 0) {
	    this.selectOperator(this.operators.get(0));
	}

	if (this.units.size() > 0) {
	    this.selectUnit(this.units.get(0));
	}
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

	for (String op : this.operators) {
	    result.add(TriggerArgument.operatorDisplayStrings.get(op));
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