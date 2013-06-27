package de.fuberlin.whitespace;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.fuberlin.whitespace.regelbau.logic.Rule;

public class RegelAdapter extends ArrayAdapter<Rule> {
RuleMainviewActivity rulemain;
	public RegelAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		rulemain = (RuleMainviewActivity)context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RuleView ruleview = new RuleView(parent.getContext(), getItem(position),rulemain);
		return ruleview.getView(parent);
	}

}
