package de.fuberlin.whitespace.regelbau.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.Log;

import de.exlap.DataElement;
import de.exlap.DataListener;
import de.exlap.DataObject;
import de.exlap.ExlapClient;
import de.exlap.ExlapException;

public class ProxyClient implements DataListener {

	public static final int INTERFACE_MESSAGE_INTERVAL = 100;

	private static ProxyClient _instance = null;

	private HashMap<String, Set<Trigger>> listeners;

	private ExlapClient ec;

	private ProxyClient () {
		this.listeners = new HashMap<String, Set<Trigger>>();

		this.ec = new ExlapClient("<address>");
		this.ec.addDataListener(this);
		this.ec.connect();

		//if (this.ec.isConnected()) {
		//    Log.i("ConnectionHelper", "isConnected on Port" + address);
		//} else {
		//    Log.i("ConnectionHelper", "Unable to connect to: " + address);
		//}
	}

	public static ProxyClient get () {
		if (_instance == null) {
			_instance = new ProxyClient();
		}

		return _instance;
	}

	public void addListener (String dataObjectUrl, Trigger listener) throws IllegalArgumentException, IOException, ExlapException {
		if (!listeners.containsKey(dataObjectUrl)) {
			listeners.put(dataObjectUrl, new HashSet<Trigger>());
		}

		//subscribe to interface
		if (listeners.get(dataObjectUrl).isEmpty()) {
			ec.subscribeObject(dataObjectUrl, 100);
		}

		listeners.get(dataObjectUrl).add(listener);
	}

	public void removeListener (String dataObjectUrl, Trigger listener) throws IllegalArgumentException, IOException, ExlapException {
		if (listeners.containsKey(dataObjectUrl)) {
			listeners.get(dataObjectUrl).remove(listener);
		}

		if (listeners.get(dataObjectUrl).isEmpty()) {
			ec.unsubscribeObject(dataObjectUrl);
		}
	}

	@Override
	public void onData (DataObject dataObject) {

		if (dataObject != null) {
			for (Trigger t : listeners.get(dataObject.getUrl())) {
				t.trigger(dataObject);
			}
		}
	}


}
