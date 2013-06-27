package de.fuberlin.whitespace.regelbau.logic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TriggerGroup repräsentiert eine thematische Gruppierung
 * von {@link TriggerVocabulary}s.
 * @author s.stugk
 *
 */
public class TriggerGroup extends HashMap<String, TriggerVocabulary> {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8017578376168437881L;
    
    private String id;

    private String word;
    
    protected TriggerGroup (String id, String word) {
	this.id = id;
	this.word = word;
    }
    
    public boolean put (TriggerVocabulary vocab) {
	return !this.containsKey(vocab.triggerId) && null != this.put(vocab.triggerId, vocab);
    }

    @Override
    public void putAll(Map<? extends String, ? extends TriggerVocabulary> map) {
	for (TriggerVocabulary vocab : map.values()) {
	    this.put(vocab);
	}
    }

    public boolean containsTriggerId (String id) {
	return this.containsKey(id);
    }

    public List<TriggerVocabulary> getVocabularies() {
	List<TriggerVocabulary> result = new ArrayList<TriggerVocabulary>();
	result.addAll(this.values());
	return result;
    }
    
    @Override
    public String toString() {
	return this.word;
    }

    @Override
    public Object clone () {
	
	TriggerGroup clone = new TriggerGroup(this.id, this.word);
	clone.putAll(this);
	
	return clone;
    }
    
    @Override
    public boolean equals (Object o) {
	return (o instanceof TriggerGroup) && ((TriggerGroup) o).id.equals(this.id);
    }
}
