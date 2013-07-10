package de.fuberlin.whitespace.regelbau.logic.triggers;

public class IntervalTimeTrigger extends AbstractTimeTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 7130688115802923326L;

    @Override
    protected void onWakeUp() {
	
	Param p = this.getParam("Minutes");	
	long interval = Long.valueOf(p.value());
	
	interval *= 60000;
	
	this.obtainTimerEvent(interval);
    }

}
