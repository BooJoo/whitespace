package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.util.Date;

public class ElapsedTimeTrigger extends AbstractTimeTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 2303272515268605671L;

    @Override
    protected void onWakeUp() {
	
	Param p = this.getParam("Minutes");	
	long interval = Long.valueOf(p.value());
	
	interval *= 60000;
	
	this.obtainTimerEvent(new Date(System.currentTimeMillis() + interval));
    }

}
