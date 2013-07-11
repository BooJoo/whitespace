package de.fuberlin.whitespace.regelbau.logic.triggers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import de.exlap.DataObject;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

/**
 * Die Oberklasse aller zeitbasierten Auslöser.
 * Sie stellt die Timer-Erzeugung und -verwaltung
 * bereit.
 *
 */
public abstract class AbstractTimeTrigger extends Trigger {

    /**
     * 
     */
    private static final long serialVersionUID = -8515950629045087185L;
    
    /**
     * Globaler Timer für alle Instanzen
     */
    private static Timer _timer = null;

    /**
     * Anzahl der laufenden Tasks
     */
    private static AtomicInteger _taskCount;

    /**
     * Task der aktuellen Instanz
     */
    private transient TimerTask task = null;
    
    private boolean state = false;
    
    private static void init() {
	_taskCount = new AtomicInteger(0);
	_timer = new Timer();
    }
    
    @Override
    protected void onFallAsleep() {
	if (this.task != null) {
	    this.task.cancel();
	    this.task = null;
	}
	
	if (_taskCount.decrementAndGet() == 0) {
	    _timer.cancel();
	    _timer = null;
	}
    }
    
    /**
     * Erzeugt einen TimerTask mit gegebenen Zieldatum.
     * @param when
     */
    protected void obtainTimerEvent (Date when) {
	if (_timer == null) {
	    AbstractTimeTrigger.init();
	}

	_taskCount.incrementAndGet();
	this.task = this.buildTask();

	_timer.schedule(this.task, when);

    }

    /**
     * Erzeugt einen TimerTask, der im angegebenen
     * Millisekunden-Intervall ausgeführt wird.
     * @param interval
     */
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
    
    /**
     * Erzeugt einen TimerTask, der die
     * Erfüllung dieses Triggers signalisiert.
     * @return
     */
    private TimerTask buildTask () {
	return new TimerTask () {

	    @Override
	    public void run() {
		AbstractTimeTrigger.this.propagateFulfillment();
		_taskCount.decrementAndGet();
	    }};
    }
    

    @Override
    public Boolean getState() {
	return this.state;
    }

    @Override
    protected boolean isFullfilled(DataObject dataObject) {
	return false;
    }
}
