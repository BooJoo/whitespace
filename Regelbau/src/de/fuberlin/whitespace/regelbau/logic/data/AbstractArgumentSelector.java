package de.fuberlin.whitespace.regelbau.logic.data;

import android.widget.ListView;

/**
 * Ein AbstractArgumentSelector ist dafür zuständig
 * die Eingabe von Werten der in trigger-vocabularies.xml
 * definierten Argumente zu ermöglichen.
 *
 */
public abstract class AbstractArgumentSelector {

    /**
     * Das Argument, dass dieser AbstractArgumentSelector manipuliert.
     */
    private final TriggerArgument target;

    /**
     * Erzeugt einen AbstractArgumentSelector, der Werte für <tt>target</tt>
     * entgegennimmt.
     * @param target
     */
    public AbstractArgumentSelector (TriggerArgument target) {
	this.target = target;
    }
    
    /**
     * Gibt das {@link TriggerArgument} zurück, das dieser
     * AbstractArgumentSelector manipuliert.
     * @return
     */
    public TriggerArgument getTarget () {
	return this.target;
    }
    
    /**
     * Zeigt den AbstractArgumentSelector anstelle von <tt>view</tt> an
     * und informiert <tt>callback</tt> über Statusänderungen.
     * @param view
     * @param callback
     */
    public abstract void show (final ListView view, final SelectorCallback callback);

    /**
     * Ein SelectorCallback nimmt Statusänderungen des
     * AbstractArgumentSelectors entgegen.
     *
     */
    public static abstract class SelectorCallback {
	
	/**
	 * Diese Methode dient dazu, die Auswahl von Werten
	 * zu signalisieren.<br />
	 * <tt>key</tt> ein beliebiger Integer zur Identifikation der geänderten Werte.
	 * Die Festlegung seiner Semantik obliegt den konkreten Implementierungen
	 * von {@link AbstractArgumentSelector} und {@link SelectorCallback}.
	 * @param key
	 * @param value
	 */
	public void onSelection(Integer key, String value) {
	    
	}
	
	/**
	 * Diese Methode dient dazu, den Abschluss der Benutzereingabe
	 * zu signalisieren.
	 */
	public abstract void onFinished();
    }
}
