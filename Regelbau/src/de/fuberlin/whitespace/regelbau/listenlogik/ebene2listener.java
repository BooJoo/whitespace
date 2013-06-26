package de.fuberlin.whitespace.regelbau.listenlogik;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerGroup;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary;

public class ebene2listener implements OnItemClickListener {

    private View obereregelbuttonview;
    private ListView listview;

    public ebene2listener(View obereregelbuttonview,ListView listview){
	this.obereregelbuttonview = obereregelbuttonview;
	this.listview = listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
	TriggerGroup selectedTriggerGroup = (TriggerGroup) parent.getItemAtPosition(position);
	
	ArrayAdapter<TriggerVocabulary> adapter = new ArrayAdapter<TriggerVocabulary>(listview.getContext(),android.R.layout.simple_list_item_1, selectedTriggerGroup.getVocabularies());
	listview.setAdapter(adapter);
	listview.setOnItemClickListener(new ebene3listener(obereregelbuttonview, listview));

    }

}
