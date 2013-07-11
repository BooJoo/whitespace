package de.fuberlin.whitespace.regelbau;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.logic.data.AbstractArgumentSelector.SelectorCallback;

public class MyEmbeddedTextPicker {
    
    protected static final Integer TEXT_INPUT = 1;

    public MyEmbeddedTextPicker(String title, String initialValue, final ListView view, final SelectorCallback callback) {
	
	final RelativeLayout layout = (RelativeLayout) View.inflate(view.getContext(), R.layout.embedded_textpicker, null);
	
	((ViewGroup) view.getParent()).addView(layout);
	view.setVisibility(View.GONE);

	((TextView) layout.findViewById(R.id.textViewTextPickerTitle)).setText(title);
	
	Button button = (Button) layout.findViewById(R.id.buttonSubmitText);
	final EditText input = (EditText) layout.findViewById(R.id.editTextTextPickerInput);
	
	if (initialValue != null) {
	    input.setText(initialValue);
	}
	
	button.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		
		callback.onSelection(TEXT_INPUT, input.getText().toString());
		callback.onFinished();

		view.setVisibility(View.VISIBLE);
		((ViewGroup) view.getParent()).removeView(layout);
	    }
	});
    }
}

