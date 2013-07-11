package de.fuberlin.whitespace.regelbau.logic.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.SQLDB.SQLDataBase;


/**
 * RulesPool verwaltet alle vorhandenen Regeln.
 *
 */
public class RulesPool extends Service {

    public static final String RULE_ADDITION = "de.fuberlin.whitespace.regelbau.RULE_ADDITION";
    public static final String RULE_UPDATE = "de.fuberlin.whitespace.regelbau.RULE_UPDATE";
    public static final String RULE_REMOVAL = "de.fuberlin.whitespace.regelbau.RULE_REMOVAL";
    
    private SQLDataBase DB;

    protected List<Rule> rules;
    
    private ProxyClient client;
    
    private PoolHandler handler;

    @Override
    public void onCreate() {
	
	super.onCreate();
	
	this.handler = null;
	this.client = new ProxyClient(this);
	this.rules = Collections.synchronizedList(new ArrayList<Rule>());
	this.DB = new SQLDataBase(this);
	this.DB.open();
	
	for (Rule rule : DB.getAllEntries()) {
	    this.rules.add(rule);
	    
	    if (rule.isActive()) {
		rule.wakeUp(this);
	    }
	}
    }
    
    @Override
    public void onDestroy() {
	super.onDestroy();
	
	for (Rule r : this.rules) {
	    r.fallAsleep();
	}
	
	try {
	    this.client.terminate();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
	this.DB.close();
    }

    @Override
    public IBinder onBind(Intent arg0) {
	
	return new PoolBinder(this);
    }

    protected void add (Rule r) {
	
	assert(r.getId() == null && !r.isAwake());
	
	this.rules.add(r);
	this.DB.AddToDB(r);
	
	if (r.isActive()) {
	    r.wakeUp(this);
	}
	
	assert(r.getId() != null);

	this.sendBroadcast(new Intent(RulesPool.RULE_ADDITION).putExtra("rule_id", r.getId()));
    }
    
    public void update(Rule r) {
	
	assert(r.getId() != null);
	this.DB.UpdateRow(r);
	
	this.sendBroadcast(new Intent(RulesPool.RULE_UPDATE).putExtra("rule_id", r.getId()));
    }

    protected void remove (Rule r) {
	
	assert(r.getId() != null && !r.isDeleted());
	
	this.rules.remove(r);
	this.DB.RemoveFromDB(r);
	r.signalDestruction();
	
	assert(r.isDeleted());

	this.sendBroadcast(new Intent(RulesPool.RULE_REMOVAL));
    }
    
    public synchronized void setHandler (PoolHandler handler) {
	this.handler = handler;
	this.notifyAll();
    }
    
    public synchronized PoolHandler getHandler () throws InterruptedException {
	while (this.handler == null) {
	    this.wait();
	}
	
	return this.handler;
    }
    
    public ProxyClient getClient () {
	return this.client;
    }
    
}