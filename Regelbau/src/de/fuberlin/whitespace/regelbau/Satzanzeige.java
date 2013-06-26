package de.fuberlin.whitespace.regelbau;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ActionOption;
import de.fuberlin.whitespace.regelbau.logic.data.DataLoader;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerGroup;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ArgumentData;

public class Satzanzeige extends LinearLayout {

    Vector<IButtonChangeListener> listeners = new Vector<IButtonChangeListener>();

    private AbsRule rule;

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
	
	this.rule = rule;
	this.setOrientation(LinearLayout.VERTICAL);

	// Button A und Vocabulary initialisieren
	this.setActionVocabulary(this.dataLoader.getAllActionVocabularies().get(0));
	this.updateButtonA();
	this.buttona.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		ActionChanged();
	    }
	});

	// Buttion B und Option initialisieren
	this.currentActionVocabulary.getOptions().get(0).select();
	this.updateButtonB();
	this.buttonb.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		ActionOptionsChanged();
	    }
	});
	
	List<TriggerGroup> tmpGroups = this.dataLoader.getTriggerGroupsByActionVocabulary(this.currentActionVocabulary);
	ArgumentData tmpArgData;
	
	if (tmpGroups.size() > 0) {
	    this.setTriggerVocabulary(tmpGroups.get(0).getVocabularies().get(0));
	    
	    tmpArgData = this.currentTriggerVocabulary.getArgumentData().get(0);
	    
	    if (tmpArgData.numOperators() > 0) {
		tmpArgData.selectOperator(tmpArgData.getOperators().get(0));
	    }
	    
	    tmpArgData.selectValue(tmpArgData.getValues().get(0));
	    
	    if (tmpArgData.numUnits() > 0) {
		tmpArgData.selectUnit(tmpArgData.getUnits().get(0));
	    }
	}
	
	this.updateButtonC();
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
    
    public void updateButtonA() {
	if (this.currentActionVocabulary != null) {
	    this.buttona.setText(this.currentActionVocabulary.getWord());
	}
    }

    public void updateButtonB (){
	
	List<ActionOption> activeOptions = ActionOption.getSelection(this.currentActionVocabulary);
	int count = Math.min(3, activeOptions.size());
	String buttonLabel = activeOptions.get(0).getWord();

	for (int i = 1; i < count; i++) {

	    if (i == count - 1) {
		buttonLabel += " und ";
	    } else {
		buttonLabel += ", ";
	    }

	    buttonLabel += activeOptions.get(i);
	}
	
	this.buttonb.setText(buttonLabel);
    }
    
    public void updateButtonC (){

	String buttonLabel = "";

	if (this.currentTriggerVocabulary != null) {
	    
	    buttonLabel += this.currentTriggerVocabulary.getWord();

	    if (this.currentTriggerVocabulary.getArgumentData().size() > 0) {

		ArgumentData arg = this.currentTriggerVocabulary.getArgumentData().get(0);

		if (arg.getSelectedOperator() != null) {
		    buttonLabel += " " + arg.getSelectedOperatorDisplaystring();
		}

		if (arg.getSelectedValue() != null) {
		    buttonLabel += " " + arg.getSelectedValue();

		    if (arg.getSelectedUnit() != null) {
			buttonLabel += " " + arg.getSelectedUnit();
		    } else if (arg.numUnits() == 1) {
			buttonLabel += " " + arg.getUnits().get(0);
		    }
		}
	    }
	}
	
	buttonc.setText(buttonLabel);
    }

    public DataLoader getDataLoader () {
	return this.dataLoader;
    }

    public ActionVocabulary getCurrentActionVocabulary() {
        return currentActionVocabulary;
    }
    
    public void setActionVocabulary(ActionVocabulary newVocabulary) {
	this.currentActionVocabulary = newVocabulary;
    }
    
    public TriggerVocabulary getCurrentTriggerVocabulary() {
        return currentTriggerVocabulary;
    }

    public void setTriggerVocabulary(TriggerVocabulary newVocabulary) {
	this.currentTriggerVocabulary = newVocabulary;
    }
}
