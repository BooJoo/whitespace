package de.fuberlin.whitespace.regelbau.listenlogik;

import java.util.Map;

import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.MyEmbeddedTextPicker;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector.SelectorCallback;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ActionOption;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ArgumentValue;

public class ebene1listener implements OnItemClickListener {

    private View obereregelbuttonview;
    private ListView listview;
    private Satzanzeige satzanzeige;

    public ebene1listener (View obereregelbuttonview, final ListView listview){
	this.obereregelbuttonview = obereregelbuttonview;
	this.listview = listview;
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
	satzanzeige = (Satzanzeige) obereregelbuttonview;

	Drawable img = listview.getContext().getResources().getDrawable(drawable.check_white_small);
	
	ActionOption currentOption;
	ActionVocabulary currentVocabulary = satzanzeige.getActionVocabulary();
	SparseBooleanArray checked = listview.getCheckedItemPositions();
	
	for (int i = 0; i < listview.getCount(); i++){
	    if (checked.get(i)) {
		currentOption = (ActionOption) parent.getItemAtPosition(position);
		currentOption.select();
		
		final Map<String, ArgumentValue> argValues = currentOption.getArgValues();
		
		for (final String key : argValues.keySet()) {
		    if (argValues.get(key).isEditable()) {
			
			new MyEmbeddedTextPicker(argValues.get(key).getEditingDisplayString(), argValues.get(key).getInitialValue(), listview, new SelectorCallback() {

			    @Override
			    public void onSelection(Integer selectionKey, String value) {
				argValues.get(key).setEditedValue(value);
			    }
			    
			    @Override
			    public void onFinished() {/* nothing to do here */}
			    
			});
		    }
		}
	    }
	}	

	satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
	
	if (ActionOption.numSelectedItems(currentVocabulary) > 0) {
	    satzanzeige.getButtonb().setText(currentVocabulary.getSelectedActionOptionsString());
	    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	} else {
	    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
	}
    }

}
