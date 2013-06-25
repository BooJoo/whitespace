package de.fuberlin.whitespace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import de.fuberlin.whitespace.regelbau.MainActivity;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.actions.ShowMessage;

/**
 * Neue Regelansicht für Sprintpraesentation
 * @author Christoph
 *
 */
public class RuleView {
	Rule rule;
	private LayoutInflater mInflater;
	String[] res;
	private RuleMainviewActivity main;
	OnClickListener ocl = new View.OnClickListener(){
		//action.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Bearbeitung der Regel aufrufen
			main.rufeRegelkontruktionAuf(rule);
		}
	};

	public RuleView(Context context,Rule rule, RuleMainviewActivity main) {
		super();
		this.rule = rule;
		this.main = main;
		try{
		res = ((ShowMessage)(rule.getActions().get(0))).getParameter();
		}catch(Exception e){}
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(ViewGroup parent){
		View v = mInflater.inflate(R.layout.regelansicht, parent, false);
		Button action = (Button)v.findViewById(R.id.buttonAction);
		ImageButton actionopt = (ImageButton)v.findViewById(R.id.buttonActionOpt);
		Button trigger = (Button)v.findViewById(R.id.buttonTrigger);
		try{
		action.setText(res[0]+" "+res[1]);
		//actionopt.setText(res[1]);
		trigger.setText(res[2]);
		}catch(Exception e){}
		action.setOnClickListener(ocl);
		actionopt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), ScherzActivity.class);
		    	v.getContext().startActivity(i);
			}
		});
		trigger.setOnClickListener(ocl);
		return v;
	}
	
	
}
