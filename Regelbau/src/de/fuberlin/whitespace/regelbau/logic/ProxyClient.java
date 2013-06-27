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

	private static HashMap<String, Set<Trigger>> listeners;

	private ExlapClient ec;

	private ProxyClient () {
		
		listeners = new HashMap<String, Set<Trigger>>();

		this.ec = new ExlapClient("socket://192.168.1.76:28500");
		this.ec.addDataListener(this);
		this.ec.connect();
		System.out.println("connection hergestellt");
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
		System.out.println("Paket hinzufügen");
		if (!listeners.containsKey(dataObjectUrl)) {
			listeners.put(dataObjectUrl, new HashSet<Trigger>());
		}
		System.out.println("Paket pAKET NAME OK");
		//subscribe to interface
		if (listeners.get(dataObjectUrl).isEmpty()) {
			//ec.subscribeObject(dataObjectUrl, 100);
		}
		System.out.println("listener akzeptiert");
		listeners.get(dataObjectUrl).add(listener);
		System.out.println("Pc return");
	}

	public void removeListener (String dataObjectUrl, Trigger listener) throws IllegalArgumentException, IOException, ExlapException {
		if (listeners.containsKey(dataObjectUrl)) {
			listeners.get(dataObjectUrl).remove(listener);
		}

		if (listeners.get(dataObjectUrl).isEmpty()) {
			//ec.unsubscribeObject(dataObjectUrl);
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
