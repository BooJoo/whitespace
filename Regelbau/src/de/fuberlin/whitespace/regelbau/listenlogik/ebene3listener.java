package de.fuberlin.whitespace.regelbau.listenlogik;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector.SelectorCallback;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerArgument;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;

public class ebene3listener implements OnItemClickListener {

    private View obereregelbuttonview;
    private ListView listview;

    public ebene3listener(View obereregelbuttonview, ListView listview){
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

	List<TriggerArgument> arguments = selectedVocabulary.getArgumentData();

	if (arguments.size() > 0) {	// Trigger hat Argumente

	    // TODO: Bisher berücksichtigen wir nur das erste Argument.
	    final TriggerArgument currentArg = arguments.get(0);

	    currentArg.resetSelection();
	    satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());
	    
	    currentArg.getSelector().show(listview, new SelectorCallback () {

		@Override
		public void onFinished() {
		    
		    // Auswahl sichtbar machen.
		    satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());

		    satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
		    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
		    satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
		}
		
	    });

	} else {			// Trigger hat keine Argumente
	    
	    satzanzeige.getButtonc().setText(selectedVocabulary.getSelectedTriggerString());

	    satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	    satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );

	}
    }

}
