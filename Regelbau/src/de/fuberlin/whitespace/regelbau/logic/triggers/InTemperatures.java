package de.fuberlin.whitespace.regelbau.logic.triggers;


public class InTemperatures extends AbstractTemperatureTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 3743960221092707921L;

    @Override
    protected String getThresholdObjectUrl() {
	return "InteriorTemperature";
    }
    
}
