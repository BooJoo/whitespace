package de.fuberlin.whitespace.regelbau;

public abstract class AbsRule {
	
	private String name;
	public AbsRule(String name){
		this.setName(name);
	}
	
	
	/**
	 * Gibt true zurück, falls die Regel ausgelöst ist.
	 * @return
	 */
	public abstract boolean Triggered();
	/**
	 * Implementiert die auszuführende Aktion.
	 */
	public abstract void Action();


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
