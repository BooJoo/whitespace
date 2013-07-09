package de.fuberlin.whitespace;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.RegelBearbeitenActivity;
import de.fuberlin.whitespace.regelbau.logic.Rule;

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
			main.rufeRegelkonstruktionAuf(rule);
		}
	};

	public RuleView(Context context, Rule rule, RuleMainviewActivity main) {
		super();
		this.rule = rule;
		this.main = main;
		try{
		//res = ((ShowMessage)(rule.getActions().get(0))).getParameter();
		}catch(Exception e){}
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(ViewGroup parent){
		View v = mInflater.inflate(R.layout.regel_kapsel, parent, false);
		Button action = (Button)v.findViewById(R.id.buttonAction);
		ImageButton actionopt = (ImageButton)v.findViewById(R.id.buttonActionOpt);
		Button trigger = (Button)v.findViewById(R.id.buttonTrigger);
		try {
		    action.setText(this.rule.getActions().get(0).toString());
		    trigger.setText(this.rule.getTriggers().get(0).toString());
		}catch(Exception e){}
		action.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), RegelBearbeitenActivity.class);
				Bundle b = new Bundle();
			    b.putLong("rule_id", rule.getId());
			    i.putExtras(b);
			    main.startActivityForResult(i, 667);
//				new Voice().Do(v.getContext());
			}
		});
		actionopt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), RegelBearbeitenActivity.class);
				Bundle b = new Bundle();
			    b.putLong("rule_id", rule.getId());
			    i.putExtras(b);
			    main.startActivityForResult(i, 667);
//				Intent i = new Intent(v.getContext(), GoogleMapsActivity.class);
//				v.getContext().startActivity(i);
			}
		});
		trigger.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), RegelBearbeitenActivity.class);
				Bundle b = new Bundle();
			    b.putLong("rule_id", rule.getId());
			    i.putExtras(b);
			    main.startActivityForResult(i, 667);
			}
		});
		
		return v;
	}
	
	
}
