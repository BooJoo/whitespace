package de.fuberlin.whitespace;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import de.fuberlin.whitespace.regelbau.MainActivity;
import de.fuberlin.whitespace.regelbau.R;
import de.fuberlin.whitespace.regelbau.RegelBearbeitenActivity;
import de.fuberlin.whitespace.regelbau.logic.Rule;
import de.fuberlin.whitespace.regelbau.logic.pool.PoolBinder;
import de.fuberlin.whitespace.regelbau.logic.pool.PoolHandler;
import de.fuberlin.whitespace.regelbau.logic.pool.RulesPool;
import de.fuberlin.whitespace.regelbau.logic.pool.UpdateReceiver;

public class RuleMainviewActivity extends Activity {
    
    private PoolBinder pool;
    private ServiceConnection poolConnection;
    private UpdateReceiver updateReceiver;

    private PoolHandler handler;
    
    private RegelAdapter myregeladapter;
    private GridView gridview;
    
    private static RuleMainviewActivity _instance = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.title);
	
	System.out.println("regelbau.MainActivity: onCreate");
	
	pool = null;
	poolConnection = null;
	updateReceiver = null;
	handler = null;
	
	this.startService(new Intent(this, RulesPool.class));
	
	myregeladapter = new RegelAdapter(this, android.R.layout.simple_list_item_1);
	
	Button addbutton = (Button) findViewById(R.id.addbutton);
	addbutton.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
		rufeRegelkonstruktionAuf();
	    }
	});

	gridview = (GridView) findViewById(R.id.gridView1);
	gridview.setAdapter(myregeladapter);

	_instance = this;
	
	this.poolConnection = new ServiceConnection () {


	    @Override
	    public void onServiceConnected(ComponentName component, IBinder binder) {

		pool = (PoolBinder) binder;
		handler = new PoolHandler(RuleMainviewActivity.this, pool);
		pool.setHandler(handler);
		
		updateReceiver = new UpdateReceiver(myregeladapter, pool);
		

		IntentFilter filter = new IntentFilter();
		filter.addAction(RulesPool.RULE_ADDITION);
		filter.addAction(RulesPool.RULE_UPDATE);
		filter.addAction(RulesPool.RULE_REMOVAL);
		

		RuleMainviewActivity.this.registerReceiver(
			updateReceiver,
			filter);
	    }

	    @Override
	    public void onServiceDisconnected(ComponentName arg0) {
		pool = null;
		updateReceiver = null;
	    }
	    
	};
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
	super.onPostCreate(savedInstanceState);
	
	this.bindService(
		new Intent(this, RulesPool.class),
		this.poolConnection,
		Context.BIND_WAIVE_PRIORITY);
    }
    
    @Override
    protected void onDestroy() {
	super.onDestroy();

	System.out.println("regelbau.View: onDestroy");

	this.stopService(new Intent(this, RulesPool.class));

	this.unbindService(this.poolConnection);
	
	if (this.updateReceiver != null) {
	    this.unregisterReceiver(this.updateReceiver);
	    this.updateReceiver = null;
	}

	this.pool = null;
    }
    
    public void rufeRegelkonstruktionAuf(){
	
    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
    	startActivityForResult(i, 666);
    }
    
    public void rufeRegelkonstruktionAuf(Rule rule){
    	
    	Intent i = new Intent(getApplicationContext(), RegelBearbeitenActivity.class);
    	Bundle b = new Bundle();
    	b.putLong("rule_id", rule.getId());
    	i.putExtras(b);
    	startActivityForResult(i, 667);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	if (resultCode == Activity.RESULT_OK) {
	    if (requestCode == 666) {
		Toast.makeText(this, "Regel wurde hinzugefügt.", Toast.LENGTH_LONG).show();
	    } else if (requestCode == 667) {
		if (data.getBooleanExtra("deleted", false)) {
		    Toast.makeText(this, "Regel wurde gelöscht.", Toast.LENGTH_LONG).show();
		} else {
		    Toast.makeText(this, "Regel wurde gespeichert.", Toast.LENGTH_LONG).show();
		}
	    }
	}
   }

    public static RuleMainviewActivity getInstance () {
    	return _instance;
    }
}
