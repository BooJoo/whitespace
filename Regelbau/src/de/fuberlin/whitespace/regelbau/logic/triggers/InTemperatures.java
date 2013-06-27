package de.fuberlin.whitespace.regelbau.logic.triggers;


public class InTemperatures extends AbstractThresholdTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 3743960221092707921L;

    @Override
    public String getThresholdObjectUrl() {
	return "InTemperature";
    }
    
}
