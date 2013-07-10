package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.util.Calendar;
import java.util.Date;

import de.fuberlin.whitespace.regelbau.logic.Trigger.Param;

public class DaytimeTrigger extends AbstractTimeTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = -1046135031522940760L;

    @Override
    protected void onWakeUp() {

	Param p = this.getParam("Minutes");	
	
	Integer minutes = Integer.valueOf(p.value());
	Integer hours = minutes / 60;
	minutes = minutes - hours * 60;
	
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR_OF_DAY, hours);
	cal.set(Calendar.MINUTE, minutes);
	cal.set(Calendar.SECOND, 0);
	
	this.obtainTimerEvent(cal.getTime());
    }

}
