package de.fuberlin.whitespace.regelbau.listenlogik;

import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ActionOption;

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

	Drawable img = listview.getContext().getResources().getDrawable( drawable.mycheck );
	
	ActionOption currentOption;
	ActionVocabulary currentVocabulary = satzanzeige.getCurrentActionVocabulary();
	SparseBooleanArray checked = listview.getCheckedItemPositions();
	
	for (int i = 0; i < listview.getCount(); i++){
	    if (checked.get(i)) {
		currentOption = (ActionOption) parent.getItemAtPosition(position);
		currentOption.select();
	    }
	}	

	satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
	
	if (ActionOption.numSelectedItems(currentVocabulary) > 0) {
	    satzanzeige.updateButtonB();
	    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
	} else {
	    satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
	}
    }

}
