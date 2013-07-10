package de.fuberlin.whitespace.regelbau;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerGroup;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;

public class Satzanzeige extends LinearLayout {

    Vector<IButtonChangeListener> listeners = new Vector<IButtonChangeListener>();
    
    private Button buttona;
    private Button buttonb;
    private Button buttonc; 

    private DataLoader dataLoader;

    private ActionVocabulary currentActionVocabulary;
    //private List<ActionOption> currentVocabularyOptions;
    
    private TriggerVocabulary currentTriggerVocabulary;

    public Button getButtona() {
	return buttona;
    }

    public Button getButtonb() {
	return buttonb;
    }

    public Button getButtonc() {
	return buttonc;
    }


    public Satzanzeige(Context context, DataLoader dataLoader, AbsRule rule, Button b1, Button b2, Button b3) {

	super(context);

	buttona = b1;
	buttonb = b2;
	buttonc = b3;

	this.dataLoader = dataLoader;
	
	this.setOrientation(LinearLayout.VERTICAL);

	// Button A und Vocabulary initialisieren
	this.setActionVocabulary(this.dataLoader.getAllActionVocabularies().get(0));
	this.buttona.setText(this.currentActionVocabulary.getSelectedActionString());
	this.buttona.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		ActionChanged();
	    }
	});

	// Buttion B und Option initialisieren
	this.currentActionVocabulary.getOptions().get(0).select();
	this.buttonb.setText(this.currentActionVocabulary.getSelectedActionOptionsString());
	this.buttonb.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		ActionOptionsChanged();
	    }
	});
	
	List<TriggerGroup> tmpGroups = this.dataLoader.getTriggerGroupsByActionVocabulary(this.currentActionVocabulary);
	
	if (tmpGroups.size() > 0) {
	    this.setTriggerVocabulary(tmpGroups.get(0).getVocabularies().get(0));
	    this.buttonc.setText(this.currentTriggerVocabulary.getSelectedTriggerString());
	}
	
	this.buttonc.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		ConditionChanged();
	    }
	});
    }

    public void registerForButtonChanges(IButtonChangeListener bcl){
	listeners.add(bcl);
    }
    /**
     * Soll aufgerufen werden, wenn der Button für die Aktion geändert wurde.
     */
    public void ActionChanged(){
	for(int i = 0; i < listeners.size(); i++){
	    listeners.get(i).aktionchanged((String)buttona.getText());
	}
    }
    /**
     * Soll aufgerufen werden, wenn der Button für die Aktion geändert wurde.
     */
    public void ActionOptionsChanged(){
	for(int i = 0; i < listeners.size(); i++){
	    listeners.get(i).aktionoptionschanged((String)buttonb.getText(),(String)buttona.getText());
	}
    }
    /**
     * Soll aufgerufen werden, wenn der Button für den Auslöser geändert wurde.
     */
    public void ConditionChanged(){
	for(int i = 0; i < listeners.size(); i++){
	    listeners.get(i).ausloeserchanged((String)buttonc.getText(), (String)buttona.getText(),(String)buttonb.getText());
	}
    }
    
    public DataLoader getDataLoader () {
	return this.dataLoader;
    }

    public ActionVocabulary getActionVocabulary() {
        return currentActionVocabulary;
    }
    
    public void setActionVocabulary(ActionVocabulary newVocabulary) {
	this.currentActionVocabulary = newVocabulary;
    }
    
    public TriggerVocabulary getTriggerVocabulary() {
        return currentTriggerVocabulary;
    }

    public void setTriggerVocabulary(TriggerVocabulary newVocabulary) {
	this.currentTriggerVocabulary = newVocabulary;
    }
}
