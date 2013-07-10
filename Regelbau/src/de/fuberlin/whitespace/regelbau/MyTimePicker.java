package de.fuberlin.whitespace.regelbau;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector;
import de.fuberlin.whitespace.regelbau.logic.data.TriggerArgument;

/**
 * Der Timepicker kann verwendet werden um Argumentewerte
 * einzugeben, deren Typ in trigger-vocabularies.xml als
 * <tt>TimeInterval</tt> oder <tt>Timestamp</tt> deklariert
 * ist.
 *
 */
public class MyTimePicker extends AbstractArgumentSelector {

    public MyTimePicker(TriggerArgument target) {
	super(target);
    }

    @Override
    public void show(final ListView view, final SelectorCallback callback) {
	
	final LinearLayout layout = (LinearLayout) View.inflate(view.getContext(), R.layout.mytimepicker, null);
	layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		
	final NumberPicker hours = (NumberPicker) layout.findViewById(R.id.pickerHours);
	final NumberPicker minutes = (NumberPicker) layout.findViewById(R.id.pickerMinutes);
	
	hours.setMaxValue(23);
	minutes.setMaxValue(59);
	
	Button submitButton = (Button) layout.findViewById(R.id.buttonSubmit);
	submitButton.setOnClickListener(new OnClickListener () {

	    @Override
	    public void onClick(View arg0) {
		
		// ausgewählten Wert in Minuten umrechnen und an das Argument übergeben
		MyTimePicker.this.getTarget().selectValue(String.valueOf(hours.getValue() * 60 + minutes.getValue()));
		
		callback.onFinished();
		
		view.setVisibility(View.VISIBLE);
		((ViewGroup) view.getParent()).removeView(layout);
		
	    }
	    
	});
	
	((ViewGroup) view.getParent()).addView(layout);
	view.setVisibility(View.GONE);
    }

}
