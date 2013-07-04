package de.fuberlin.whitespace.regelbau.listenlogik;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.R.drawable;
import de.fuberlin.whitespace.regelbau.Satzanzeige;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary;
import de.fuberlin.whitespace.regelbau.logic.data.ActionVocabulary.ActionOption;

/**
 * Erste Menuebene
 * @author Christoph
 *
 */
public class ebene0listener implements OnItemClickListener {

	private View obereregelbuttonview;
	private ListView listview;
	Satzanzeige satzanzeige;
	
	public ebene0listener(View obereregelbuttonview, ListView listview){
		this.obereregelbuttonview = obereregelbuttonview;
		this.listview = listview;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    
		satzanzeige = (Satzanzeige) obereregelbuttonview;
		
		ActionVocabulary newVocabulary;
		ActionOption newActionOption;
		//List<TriggerOption> newTriggerOptions;
		
		Drawable img = listview.getContext().getResources().getDrawable(drawable.mycheck);
		
		newVocabulary = (ActionVocabulary) parent.getItemAtPosition(position);
		ActionOption.clearSelection(newVocabulary);
		newActionOption = newVocabulary.getOptions().get(0);

		satzanzeige.setActionVocabulary(newVocabulary);
		newActionOption.select();
		
		satzanzeige.getButtona().setText(newVocabulary.getSelectedActionString());
		satzanzeige.getButtonb().setText(newVocabulary.getSelectedActionOptionsString());
		
		satzanzeige.getButtona().setCompoundDrawablesWithIntrinsicBounds(null, null, img, null );
		satzanzeige.getButtonb().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
		satzanzeige.getButtonc().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
		
		listview.setAdapter(null);
	}

}
