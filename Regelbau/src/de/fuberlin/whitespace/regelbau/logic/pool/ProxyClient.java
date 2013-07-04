package de.fuberlin.whitespace.regelbau.logic.pool;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.exlap.DataListener;
import de.exlap.DataObject;
import de.exlap.ExlapClient;
import de.exlap.discovery.DiscoveryListener;
import de.exlap.discovery.DiscoveryManager;
import de.exlap.discovery.ServiceDescription;
import de.fuberlin.whitespace.regelbau.MyPickerCallback;
import de.fuberlin.whitespace.regelbau.MyTextPicker;
import de.fuberlin.whitespace.regelbau.logic.Trigger;

/**
 * Der ProxyClient verwaltet Verbindung zum und 
 * Datenobjekt-Abonnements beim Proxy.
 *
 */
public class ProxyClient {

    /**
     * Standard-Fallback-Adresse, falls discovery scheitert
     */
    public static final String DEFAULT_FALLBACK_ADDRESS = "192.168.178.20:28500";

    /**
     * Nachrichtenintervall der Proxy-Verbindung.
     */
    public static final int INTERFACE_MESSAGE_INTERVAL = 500;

    /**
     * zugehöriger Pool
     */
    private RulesPool pool;

    /**
     * alle derzeit angemeldenen Abonnementen von Datenobjekten
     */
    private HashMap<String, Set<Trigger>> listeners;

    /**
     * der aktuelle {@link Worker} (verantwortlich für Kommunikation mit dem Proxy)
     */
    private Worker worker;

    /**
     * der Kontrollnachrichten-Queue des {@link #worker}
     */
    private BlockingQueue<WorkerJob> controlQueue;

    private ExlapClient ec;

    public ProxyClient (RulesPool parent) {

	this.pool = parent;
	this.listeners = new HashMap<String, Set<Trigger>>();
	this.ec = null;
	this.controlQueue = new LinkedBlockingQueue<WorkerJob>();
	this.worker = new Worker();

	DiscoveryManager disco;

	try {

	    disco = new DiscoveryManager(DiscoveryManager.SCHEME_SOCKET);
	    disco.discoverServices(worker, null, true);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Setzt die Verbindung zurück und verbindet neu
     * mit <tt>address</tt>.
     * @param address
     */
    public void reinitialize(String address) {

	synchronized (this.worker) {
	    if (this.ec != null && this.ec.isConnected()) {
		this.ec.disconnect();
	    }

	    this.worker.connect(address);
	}

    }

    /**
     * Trennt die Verbindung zum Proxy und beendet alle
     * internen Prozesse.
     * @throws InterruptedException
     */
    public void terminate() throws InterruptedException {
	this.controlQueue.put(new WorkerJob(WorkerJob.Action.TERMINATION));
    }

    /**
     * Registriert <tt>listener</tt> als Abonnenten für Objekte des Typs <tt>dataObjectUrl</tt>.
     * @param dataObjectUrl
     * @param listener
     * @throws InterruptedException
     */
    public void addListener (String dataObjectUrl, Trigger listener) throws InterruptedException {

	WorkerJob job = new WorkerJob(WorkerJob.Action.SUBSCRIPTION);
	job.dataObjectUrl = dataObjectUrl;
	job.subscriber = listener;

	this.controlQueue.put(job);
    }

    /**
     * Widerruft das Abonnement von <tt>listener</tt> für Objekte des Typs <tt>dataObjectUrl</tt>.
     * @param dataObjectUrl
     * @param listener
     * @throws InterruptedException
     */
    public void removeListener (String dataObjectUrl, Trigger listener) throws InterruptedException {

	WorkerJob job = new WorkerJob(WorkerJob.Action.UNSUBSCRIPTION);
	job.dataObjectUrl = dataObjectUrl;
	job.subscriber = listener;

	this.controlQueue.put(job);
    }

    /**
     * Der Worker ist der eigentliche Kern des ProxyClient. Er nimmt
     * Pakete vom Proxy entgegen und leitet sie an die Abonnenten weiter.
     *
     */
    private class Worker extends Thread implements DataListener, DiscoveryListener {

	public Worker () {
	    this.start();
	}	

	@Override
	public void run () {

	    // Blockieren, bis ExlapClient-Instanz verfügbar
	    try {
		synchronized (this) {
		    while (ProxyClient.this.ec == null || !ProxyClient.this.ec.isConnected()) {
			this.wait();
		    }
		}
	    } catch (InterruptedException e) {
		throw new RuntimeException(e);
	    }

	    WorkerJob job;

	    while (true) {

		try {
		    job = ProxyClient.this.controlQueue.take();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		    continue;
		}

		switch (job.action) {

		// Anmeldung von Trigger für bestimmte Datenobjekte

		case SUBSCRIPTION:

		    try {

			if (!listeners.containsKey(job.dataObjectUrl)) {
			    listeners.put(job.dataObjectUrl, new HashSet<Trigger>());
			}

			//subscribe to interface
			if (listeners.get(job.dataObjectUrl).isEmpty()) {
			    ec.subscribeObject(job.dataObjectUrl, ProxyClient.INTERFACE_MESSAGE_INTERVAL);
			}

			listeners.get(job.dataObjectUrl).add(job.subscriber);

		    } catch (Exception e) {
			e.printStackTrace();
		    }

		    break;

		    // Abmeldung von Triggern von bestimmten Datenobjekten

		case UNSUBSCRIPTION:

		    try {

			if (listeners.containsKey(job.dataObjectUrl)) {
			    listeners.get(job.dataObjectUrl).remove(job.subscriber);
			}

			if (listeners.get(job.dataObjectUrl).isEmpty()) {
			    ec.unsubscribeObject(job.dataObjectUrl);
			}

		    } catch (Exception e) {
			e.printStackTrace();
		    }

		    break;

		    // Datenobjekte

		case DATA_OBJECT:

		    if (job.dataObject != null && job.dataObject.size() > 0) {
			for (Trigger t : listeners.get(job.dataObject.getUrl())) {
			    t.trigger(job.dataObject);
			}
		    }

		    // Terminierungssignal

		case TERMINATION:

		    ProxyClient.this.ec.disconnect();
		    ProxyClient.this.ec = null;

		    return;
		}
	    }
	}

	@Override
	public void discoveryEvent(int eventType, ServiceDescription serviceDescription) {
	    switch (eventType) {
	    case DiscoveryListener.SERVICE_CHANGED:
		System.out.println("CHANGED:" + serviceDescription.toString());
		break;
	    case DiscoveryListener.SERVICE_GONE:
		System.out.println("GONE   :" + serviceDescription.toString());
		break;
	    case DiscoveryListener.SERVICE_NEW:
		this.connect(serviceDescription.getAddress());
		break;
	    default:
		break;
	    }
	}

	@Override
	public void discoveryFinished(boolean arg0) {

	    // Falls der Discovery-Prozess fehlgeschlagen ist, müssen
	    // wir die Adresse des Proxy beim Nutzer erfragen.

	    final PoolHandler handler = pool.getHandler();
	    handler.post(new Runnable() {

		@Override
		public void run() {

		    new MyTextPicker(handler.getContext(), new MyPickerCallback<String>() {

			@Override
			public void valueset(String value) {
			    handler.getPoolBinder().reconnect("socket://" + value);
			}

		    }, "Bitte Adresse eingeben:",
		    ProxyClient.DEFAULT_FALLBACK_ADDRESS);
		}
	    });
	}

	@Override
	public void onData(DataObject dataObject) {
	    if (dataObject == null || dataObject.size() == 0) {
		return;
	    }

	    if(!ProxyClient.this.controlQueue.offer(new WorkerJob(dataObject))) {
		System.out.println("DataObject delivery failed for object of type '" +  dataObject.getUrl() + "'.");
	    }
	}

	public void connect (final String address) {

	    if (ProxyClient.this.ec != null && ProxyClient.this.ec.isConnected()) {
		return;
	    }

	    (new Thread(new Runnable() {
		public void run() {

		    synchronized (Worker.this) {

			System.out.println("Connecting ...");

			ProxyClient.this.ec = new ExlapClient(address);
			ProxyClient.this.ec.addDataListener(Worker.this);
			ProxyClient.this.ec.connect();

			if (ProxyClient.this.ec.isConnected()) {

			    pool.getHandler().toast("Connected.");
			    System.err.println("Connected @ " + address);

			    // Den evt. gerade blockierenden Worker aufwecken.
			    Worker.this.notifyAll();

			} else {

			    pool.getHandler().toast("Connection failed.");
			    System.err.println("Connection failed.");
			}
		    }
		}
	    })).start();
	}
    }

    /**
     * Ein WorkerJob entspricht einer konkreten Aufgabe des Worker-Threads:
     * <ul>
     * 	<li> {@link WorkerJob.Action#SUBSCRIPTION} - Datenobjekt-Abonnements
     *  <li> {@link WorkerJob.Action#UNSUBSCRIPTION} - Abmeldung von Abonnements
     *  <li> {@link WorkerJob.Action#DATA_OBJECT} - Entgegennahme und Weiterleitung von Datenobjekten
     *  <li> {@link WorkerJob.Action#TERMINATION} - Terminierung
     * </ul>
     *
     */
    private static class WorkerJob {

	private enum Action {
	    SUBSCRIPTION, UNSUBSCRIPTION, DATA_OBJECT, TERMINATION
	}

	private Action action;

	private Trigger subscriber = null;
	private DataObject dataObject = null;
	private String dataObjectUrl = null;

	public WorkerJob (Action action) {
	    this.action = action;
	}

	public WorkerJob(DataObject dataObject) {
	    this.action = Action.DATA_OBJECT;
	    this.dataObject = dataObject;
	}
    }

}
