package de.fuberlin.whitespace.regelbau;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector;
import de.fuberlin.whitespace.regelbau.logic.data.PreselectedTriggerArgument;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerArgument;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerVocabulary.ListItemValueContainer;

/**
 * Der NumberPicker kann zur Auswahl aus Aufzählungen
 * von Argumentwerten verwendet werden.
 *
 * @param <T> Der Typ der zugrundeliegenden Werte
 */
public class MyNumberPicker <T> extends AbstractArgumentSelector {
    
    boolean eingabeBeendet;
    
    private Runnable showImplementation;
    
    private ListView view;
    
    private SelectorCallback callback;

    /**
     * Dieser Konstruktor dient zur Verwendung als
     * {@link AbstractArgumentSelector}.
     * @param target
     */
    public MyNumberPicker(TriggerArgument target) {
	super(target);
	
	if (!(target instanceof PreselectedTriggerArgument)) {
	    throw new RuntimeException(
		    "The NumberPicker needs a set of preselected values and operators " +
		    "in the configuration of the given argument in trigger-vocabularies.xml");
	}
	
	this.view = null;
	this.callback = null;
	
	this.<ListItemValueContainer>init(
		((PreselectedTriggerArgument) target).getValueContainers().toArray(new ListItemValueContainer[0]),
		target.getSelectedUnit() != null ? target.getSelectedUnit() : "");
    }
    
    /**
     * Dieser Konstruktor dient zur allgemeinen Verwendung.
     * @param werte
     * @param suffix
     */
    public MyNumberPicker (final T[] werte, String suffix) {
	super(null);
	this.<T>init(werte, suffix);
    }
    
    @Override
    public void show(ListView view, SelectorCallback callback) {
	this.view = view;
	this.callback = callback;
	this.showImplementation.run();
    }
    
    protected <U>void init (final U[] werte, String suffix) {
	
	this.showImplementation = new Runnable () {

	    @Override
	    public void run() {
		final ArrayAdapter<U> adapter = new ArrayAdapter<U>(view.getContext(), android.R.layout.simple_list_item_1);
		adapter.addAll(werte);
		view.setAdapter(adapter);

		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int position = arg2;

			U wert = adapter.getItem(position);
			
			TriggerArgument target = MyNumberPicker.this.getTarget();
			
			if (target != null && wert instanceof ListItemValueContainer) {
			    target.selectValue((ListItemValueContainer) wert);
			}
			
			callback.onSelection(0, wert.toString());
			callback.onFinished();

			view.setAdapter(null);
		    }
		});
	    }
	};
    }
}
