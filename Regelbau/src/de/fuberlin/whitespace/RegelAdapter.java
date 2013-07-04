package de.fuberlin.whitespace;

import java.util.Collection;
import java.util.Comparator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.fuberlin.whitespace.regelbau.logic.Rule;

public class RegelAdapter extends ArrayAdapter<Rule> {

    RuleMainviewActivity rulemain;
    private Comparator<Rule> cmp;
    
    public RegelAdapter(Context context, int textViewResourceId) {
	super(context, textViewResourceId);
	
	rulemain = (RuleMainviewActivity)context;
	
	cmp = new Comparator<Rule> (){

	    @Override
	    public int compare(Rule lhs, Rule rhs) {
		return lhs.getId() == null ? Integer.MIN_VALUE : (-1) *  lhs.getId().compareTo(rhs.getId());
	    }

	};
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	RuleView ruleview = new RuleView(parent.getContext(), getItem(position),rulemain);
	return ruleview.getView(parent);
    }
    

    @Override
    public void add(Rule object) {
	super.add(object);
	this.sort(this.cmp);
    }

    @Override
    public void addAll(Collection<? extends Rule> collection) {
	super.addAll(collection);
	this.sort(this.cmp);
    }

    @Override
    public void addAll(Rule... items) {
	super.addAll(items);
	this.sort(this.cmp);
    }

}
