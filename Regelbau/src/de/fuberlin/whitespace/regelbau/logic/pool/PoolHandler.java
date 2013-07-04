package de.fuberlin.whitespace.regelbau.logic.pool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Der PoolHandler dient zur Kommunikation vom
 * Service {@link RulesPool} in Richtung des UI-Threads.
 *
 */
public class PoolHandler extends Handler {
    
    private Context context;
    private PoolBinder pool;

    public PoolHandler (Context ctx) {
	this.context = ctx;
    }

    public PoolHandler (Context ctx, PoolBinder pool) {
	this(ctx);
	this.pool = pool;
    }

    @Override
    public void handleMessage(Message msg) {
	super.handleMessage(msg);
	
    }

    public Context getContext() {
        return context;
    }
    
    public PoolBinder getPoolBinder() {
	return pool;
    }
    
    public void setBinder(PoolBinder pool) {
	this.pool = pool;
    }

    public void toast (final String message) {
	this.post(new Runnable() {

	    @Override
	    public void run() {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	    }
	    
	});
    }

}
