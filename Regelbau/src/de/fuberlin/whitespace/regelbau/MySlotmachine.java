package de.fuberlin.whitespace.regelbau;

import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector;
import de.fuberlin.whitespace.regelbau.logic.data.PreselectedTriggerArgument;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerArgument;
import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector.SelectorCallback;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Zeigt eine Slotmachine an.
 * @author Maria, Christoph
 *
 */
public class MySlotmachine extends AbstractArgumentSelector {

    public static final Integer SLOT_1 = 1;
    public static final Integer SLOT_2 = 2;
    public static final Integer SLOT_3 = 3;
    
    String wert1 = null;
    String wert2 = null;
    String wert3 = null;

    String ueberschrift1; String[] values1;
    String ueberschrift2; String[] values2;
    String ueberschrift3; String[] values3;
    
    public MySlotmachine(TriggerArgument target) {
	super(target);
	
	if (target instanceof PreselectedTriggerArgument) {
	    this.init((PreselectedTriggerArgument) target);
	} else {
	    throw new RuntimeException(
		    "The slotmachine needs a set of preselected values, operators " +
		    "and units in the configuration of the given argument in trigger-vocabularies.xml");
	}
    }

    public MySlotmachine(PreselectedTriggerArgument target) {
	super(target);
	this.init(target);
    }

    /**
     * 
     * @param context
     * @param listview Die zu ersetzende Listview, die nachher wieder rekonstruiert wird.
     * @param ueberschrift1
     * @param values1 Werte für die 1. Spalte
     * @param ueberschrift2
     * @param values2 Werte für die 2. Spalte
     * @param ueberschrift3
     * @param values3 Werte für die 3. Spalte
     */
    public MySlotmachine(String ueberschrift1, String[] values1, String ueberschrift2,
	    String[] values2, String ueberschrift3, String[] values3, SelectorCallback callback) {

	super(null);

	this.init(
		ueberschrift1, values1,
		ueberschrift2, values2,
		ueberschrift3, values3
		);
    }

    private void init(String ueberschrift1, String[] values1, String ueberschrift2,
	    String[] values2, String ueberschrift3, String[] values3) {

	this.ueberschrift1 = ueberschrift1;
	this.values1 = values1;
	this.ueberschrift2 = ueberschrift2;
	this.values2 = values2;
	this.ueberschrift3 = ueberschrift3;
	this.values3 = values3;
    }
    
    private void init(PreselectedTriggerArgument target) {
	this.init(
		"Operator", target.getOperatorDisplayStrings().toArray(new String[0]),
		"Wert", target.getValues().toArray(new String[0]),
		"Einheit", target.getUnits().toArray(new String[0]));
    }

    @Override
    public void show(final ListView view, final SelectorCallback callback) {

	final LinearLayout machine = (LinearLayout) View.inflate(view.getContext(), R.layout.myslotmachine, null);

	machine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	((ViewGroup) view.getParent()).addView(machine);
	view.setVisibility(View.GONE);

	Button button = (Button)machine.findViewById(R.id.kleinerFertigButton);
	button.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		
		TriggerArgument target = MySlotmachine.this.getTarget();

		if (target != null) {
		    target.selectOperatorByDisplayString(wert1);
		    target.selectValue(wert2);
		    target.selectUnit(wert3);
		}

		callback.onSelection(SLOT_1, wert1);
		callback.onSelection(SLOT_2, wert1);
		callback.onSelection(SLOT_3, wert1);
		callback.onFinished();

		view.setVisibility(View.VISIBLE);
		((ViewGroup) view.getParent()).removeView(machine);
	    }
	});

	ListView lv1 = (ListView)machine.findViewById(R.id.listView1);
	ListView lv2 = (ListView)machine.findViewById(R.id.listView2);
	ListView lv3 = (ListView)machine.findViewById(R.id.listView3);

	// Slot 1
	ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_activated_1);
	adapter1.addAll(values1);
	lv1.setAdapter(adapter1);
	lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	lv1.setOnItemClickListener(new MySlotmachineOnItemClickListener(null, lv1, new MyPickerCallback<String>() {

	    @Override
	    public void valueset (String value) {
		wert1 = value;
	    }
	}));

	// Slot 2
	ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_activated_1);
	adapter2.addAll(values2);
	lv2.setAdapter(adapter2);
	lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	lv2.setOnItemClickListener(new MySlotmachineOnItemClickListener(null, lv2, new MyPickerCallback<String>() {

	    @Override
	    public void valueset(String value) {
		wert2 = value;
	    }
	}));

	// Slot 3
	ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_activated_1);
	adapter3.addAll(values3);
	lv3.setAdapter(adapter3);
	lv3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	lv3.setOnItemClickListener(new MySlotmachineOnItemClickListener(null, lv3, new MyPickerCallback<String>() {

	    @Override
	    public void valueset(String value) {
		wert3 = value;
	    }
	}));
    }

}
