package de.fuberlin.whitespace.regelbau.listenlogik;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.MyNumberPicker;
import de.fuberlin.whitespace.regelbau.MyPickerCallback;
import de.fuberlin.whitespace.regelbau.MySlotmachine;
import de.fuberlin.whitespace.regelbau.MySlotmachineCallback;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ArgumentData;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ListItemValueContainer;

public class ebene3listener implements OnItemClickListener {

    private View obereregelbuttonview;
    private ListView listview;

    public ebene3listener(View obereregelbuttonview,ListView listview){
	this.obereregelbuttonview = obereregelbuttonview;
	this.listview = listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	final Satzanzeige satzanzeige = (Satzanzeige) obereregelbuttonview;
	final Drawable img = listview.getContext().getResources().getDrawable(drawable.check_white_small);
	
	final TriggerVocabulary selectedVocabulary = (TriggerVocabulary) parent.getItemAtPosition(position);
	
	// ausgewähltes TriggerVocabulary bekannt machen
	satzanzeige.setTriggerVocabulary(selectedVocabulary);

	List<ArgumentData> arguments = selectedVocabulary.getArgumentData();

	if (arguments.size() > 0) {	// Trigger hat Argumente

	    // TODO: Bisher berücksichtigen wir nur das erste Argument.
	    final ArgumentData currentArg = arguments.get(0);

	    currentArg.cleanSelection();
	    satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());

	    if (currentArg.numOperators() > 1 || currentArg.numUnits() > 1) {	// Es gibt Optionen für Operator/Einheit.

		new MySlotmachine (
			listview.getContext(),
			listview,
			"Operator",
			currentArg.getOperatorDisplayStrings().toArray(new String [0]),
			"Wert",
			currentArg.getValues().toArray(new String [0]),
			"Einheit",
			currentArg.getUnits().toArray(new String [0]),
			new MySlotmachineCallback() {

			    @Override
			    public void values(String operator, String wert, String einheit) {

				currentArg.selectValue(wert);
				currentArg.selectUnit(einheit);
				currentArg.selectOperatorByDisplayString(operator);

				// Auswahl sichtbar bekanntmachen.
				satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());

				satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
			    }
			});

	    } else {	// Es gibt keine Optionen für Operator/Einheit.

		new MyNumberPicker<TriggerVocabulary.ListItemValueContainer>(
			listview.getContext(),
			listview, 
			currentArg.getValueContainers().toArray(new TriggerVocabulary.ListItemValueContainer[0]),
			currentArg.numUnits() > 0 ? currentArg.getUnits().get(0) : "",
			new MyPickerCallback<TriggerVocabulary.ListItemValueContainer>() {

			    @Override
			    public void valueset (ListItemValueContainer value) {
				currentArg.selectValueByContainer(value);

				// Auswahl sichtbar.
				satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());

				satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
				
			    }
			},
			"");
	    }

	} else {			// Trigger hat keine Argumente
	    
	    satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());

	    satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    
	}
    }

}
