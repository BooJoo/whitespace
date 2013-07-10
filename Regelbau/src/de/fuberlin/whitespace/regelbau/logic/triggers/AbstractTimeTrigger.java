package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

public abstract class AbstractTimeTrigger extends Trigger {

    /**
     * 
     */
    private static final long serialVersionUID = -8515950629045087185L;
    
    private static Timer _timer = null;

    private static AtomicInteger _taskCount;

    private TimerTask task = null;

    private boolean state = false;
    
    private static void init() {
	_taskCount = new AtomicInteger(0);
	_timer = new Timer();
    }
    
    @Override
    protected void onFallAsleep() {
	if (this.task != null) {
	    this.task.cancel();
	}
	
	if (_taskCount.decrementAndGet() == 0) {
	    _timer.cancel();
	}
    }

    @Override
    public Boolean getState() {
	return this.state;
    }

    @Override
    protected boolean isFullfilled(DataObject dataObject) {
	return false;
    }


    protected void obtainTimerEvent (Date when) {
	if (_timer == null) {
	    AbstractTimeTrigger.init();
	}

	_taskCount.incrementAndGet();
	this.task = this.buildTask();

	_timer.schedule(this.task, when);

    }

    protected void obtainTimerEvent (Long interval) {
	if (_timer == null) {
	    AbstractTimeTrigger.init();
	}

	_taskCount.incrementAndGet();
	this.task = this.buildTask();
	    
	_timer.schedule(
		this.task,
		new Date(System.currentTimeMillis() + interval),
		interval);
    }
    
    private void propagateFulfillment() {
	AbstractTimeTrigger.this.state = true;
	AbstractTimeTrigger.this.getParent().signalTriggerStateChange(AbstractTimeTrigger.this);
	AbstractTimeTrigger.this.state = false;
	AbstractTimeTrigger.this.getParent().signalTriggerStateChange(AbstractTimeTrigger.this);
    }
    
    private TimerTask buildTask () {
	return new TimerTask () {

	    @Override
	    public void run() {
		AbstractTimeTrigger.this.propagateFulfillment();
		_taskCount.decrementAndGet();
	    }};
    }
}
