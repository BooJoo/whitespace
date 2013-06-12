package de.fuberlin.whitespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
	private RuleMainview main;

	public RuleView(Context context,Rule rule, RuleMainview main) {
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
		Button actionopt = (Button)v.findViewById(R.id.buttonActionOpt);
		Button trigger = (Button)v.findViewById(R.id.buttonTrigger);
		try{
		action.setText(res[0]);
		actionopt.setText(res[1]);
		trigger.setText(res[2]);
		}catch(Exception e){}
		action.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ShowMessage)(rule.getActions().get(0))).Do();
			}
		});
		trigger.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Bearbeitung der Regel aufrufen
				main.rufeRegelkontruktionAuf(rule);
			}
		});
		return v;
	}
	
	
}
