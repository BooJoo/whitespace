package de.fuberlin.whitespace.regelbau.logic.triggers;


public class AirHumidity extends AbstractThresholdTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = -4274603150539983016L;

    @Override
    protected String getThresholdObjectUrl() {
	return "AirHumidity";
    }
    
}
