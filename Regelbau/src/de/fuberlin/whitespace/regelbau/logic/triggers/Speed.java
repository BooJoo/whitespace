package de.fuberlin.whitespace.regelbau.logic.triggers;


public class Speed extends AbstractThresholdTrigger {

    /**
     * 
     */
    private static final long serialVersionUID = 7547406070421388109L;

    @Override
    public String getThresholdObjectUrl() {
	return "VehicleSpeed";
    }

}
