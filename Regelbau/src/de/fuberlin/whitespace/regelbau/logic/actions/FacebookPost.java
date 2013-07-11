package de.fuberlin.whitespace.regelbau.logic.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import de.exlap.DataObject;
import de.fuberlin.whitespace.FacebookActivity;
import de.fuberlin.whitespace.regelbau.logic.Action;

public class FacebookPost extends Action {

    @SuppressWarnings("serial")
    private static final Map<String, List<String>> informations = new HashMap<String, List<String>> () {{

	put("speed", new ArrayList<String>(){{
	    add("VehicleSpeed");
	}});

	put("consumption", new ArrayList<String>(){{
	    add("FuelConsumption");
	    add("TankLevel");
	    add("Tankage");
	}});

	put("weather", new ArrayList<String>(){{
	    add("RainIntensity");
	    add("SunIntensity");
	    add("ExteriorTemperature");
	}});

    }};

    /**
     * 
     */
    private static final long serialVersionUID = -1463104213500048018L;

    @Override
    protected void onWakeUp() {

	if (this.hasParam("information")) {
	    for (String key : this.getParam("information")) {
		for (String dataObjectUrl : informations.get(key)) {
		    this.subscribeDataObject(dataObjectUrl);
		}
	    }
	}
    }

    @Override
    public void Do(Context ctx) {

	List<String> targetInformations;
	DataObject currentObject;
	String currentValue;

	String message = "";

	if (null != (currentValue = this.peekParam("usermessage"))) {
	    message += currentValue;
	}

	targetInformations = this.getParam("information");
	targetInformations = targetInformations != null ? targetInformations : new ArrayList<String>();

	if (targetInformations.contains("speed")) {
	    if (null != (currentObject = this.getDataObject("VehicleSpeed"))) {
		message += (message.length() > 0 ? "\r\n\r\n" : "") + "Aktuelle Geschwindigkeit: " + ((Double) currentObject.getElement("VehicleSpeed").getValue()) + " km/h";
	    }
	}

	if (targetInformations.contains("consumption")) {

	    String consumptionInfo = "";
	    Double tankLevel = 0.0;
	    Double consumption = 0.0;
	    Double tankage = 0.0;

	    if (null != (currentObject = this.getDataObject("TankLevel"))) {
		
		if (currentObject.getElement("Premium").getValue() != null) {
		    tankLevel += (Double) currentObject.getElement("Premium").getValue();
		}
		
		if (currentObject.getElement("Diesel").getValue() != null) {
		    tankLevel += (Double) currentObject.getElement("Diesel").getValue();
		}
		
		if (tankLevel > 0.0) {
		    consumptionInfo += "Tankstand: " + tankLevel + " %";
		}
	    }

	    if (null != (currentObject = this.getDataObject("FuelConsumption"))) {
		consumption = ((Double) currentObject.getElement("InstantaneousValuePerMilage").getValue());
		consumptionInfo += (consumptionInfo.length() > 0 ? ", " : "") + "Kraftstoffverbrauch: " + consumption + " l/100km";
	    }

	    if (null != (currentObject = this.getDataObject("Tankage")) && tankLevel > 0 && consumption > 0) {
		if (currentObject.getElement("Premium").getValue() != null) {
		    
		    tankage += (Double) currentObject.getElement("Premium").getValue();
		}
		
		if (currentObject.getElement("Diesel").getValue() != null) {
		    tankage += (Double) currentObject.getElement("Diesel").getValue();
		}
		
		if (tankage > 0) {
		    consumptionInfo += " (Das reicht noch für " + Math.round((tankage*tankLevel)/consumption) + "km)";
		}
	    }

	    if (consumptionInfo.length() > 0) {
		message += (message.length() > 0 ? "\r\n\r\n" : "") + consumptionInfo;
	    }
	}

	if (targetInformations.contains("weather")) {
	    
	    String detailString = "";

	    // in Lux (zwischen 0.0 und 6126.0)
	    Double sunIntensity = Double.NaN;
	    // relativ (zwischen 0.0 und 1.0)
	    Double rainIntensity = Double.NaN;
	    // in °C (zwischen -60.0 und 85.0)
	    Double exteriorTemperature = Double.NaN;

	    if (null != (currentObject = this.getDataObject("SunIntensity"))) {
		sunIntensity = ((Double) currentObject.getElement("SunIntensity").getValue());
	    }

	    if (null != (currentObject = this.getDataObject("RainIntensity"))) {
		rainIntensity = ((Double) currentObject.getElement("RainIntensity").getValue());
	    }

	    if (null != (currentObject = this.getDataObject("ExteriorTemperature"))) {
		exteriorTemperature = ((Double) currentObject.getElement("ExteriorTemperature").getValue());
	    }

	    if (sunIntensity > -1.0 || rainIntensity > -1.0 || exteriorTemperature > -1.0) {
		
		message += (message.length() > 0 ? "\r\n\r\n" : "") + "Wetter: ";

		if (sunIntensity > 75000.0 && exteriorTemperature >= 20.0) {
		    message += "super";
		} else if (rainIntensity == 0.0 && sunIntensity < 30000.0 && exteriorTemperature < 18.0) {
		    message += "so la la";
		} else if (rainIntensity > 0.0 || sunIntensity < 20000.0 && exteriorTemperature <= 10.0) {
		    message += "ungemütlich";
		} else {
		    message += "ok";
		}
		
		if (!Double.isNaN(exteriorTemperature)) {
		   detailString += exteriorTemperature + " °C";
		}

		if (rainIntensity > 0.0) {
		    detailString += (detailString.length() > 0 ? ", " : "") + "regnerisch";
		} else if (sunIntensity > 75000.0) {
		    detailString += (detailString.length() > 0 ? ", " : "") + "sonnig";
		}
		
		if (detailString.length() > 0) {
		    message += " (" + detailString + ")";
		}
	    }
	}

	Intent i = new Intent(ctx, FacebookActivity.class);
	i.putExtra("message", message);
	ctx.startActivity(i);
    }

}
