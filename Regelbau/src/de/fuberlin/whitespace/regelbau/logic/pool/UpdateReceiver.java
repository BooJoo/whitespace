package de.fuberlin.whitespace.regelbau.logic.pool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import de.fuberlin.whitespace.regelbau.logic.Rule;

/**
 * UpdateReceiver empfängt Broadcasts der Typenn{@link RulesPool#RULE_ADDITION},
 * {@link RulesPool#RULE_UPDATE} und {@link RulesPool#RULE_REMOVAL} und aktualisiert
 * den gegebnen ArrayAdapter entsprechend.
 */
public class UpdateReceiver extends BroadcastReceiver {

    private ArrayAdapter<Rule> adapter;
    private PoolBinder binder;

    public UpdateReceiver (ArrayAdapter<Rule> adapter, PoolBinder binder) {
	this.adapter = adapter;
	this.binder = binder;
	
	this.adapter.addAll(this.binder.getAllRules());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
	
	Rule r;

	if (RulesPool.RULE_ADDITION.equals(intent.getAction())) {

	    r = this.binder.getRuleById(intent.getExtras().getLong("rule_id"));

	    if (r != null) {
		this.adapter.add(r);
	    }

	} else if (RulesPool.RULE_REMOVAL.equals(intent.getAction())) {

	    for (int i=0; i < this.adapter.getCount(); i++) {
		r = this.adapter.getItem(i);

		if (r.isDeleted()) {
		    this.adapter.remove(r);
		}
	    }
	}
	
	this.adapter.notifyDataSetChanged();
    }
    
    public void setPoolBinder (PoolBinder binder) {
	this.binder = binder;
    }

}
