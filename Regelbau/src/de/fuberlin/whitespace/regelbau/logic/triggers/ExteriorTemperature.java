package de.fuberlin.whitespace.regelbau.logic.triggers;


public class ExteriorTemperature extends AbstractTemperatureTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 2131966040235640289L;
    
    @Override
    protected String getThresholdObjectUrl() {
	return "ExteriorTemperature";
    }
    
}
