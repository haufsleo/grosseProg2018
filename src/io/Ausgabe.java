package io;

import model.Model;

public abstract class Ausgabe {
	private Model model;
	
	public Ausgabe(Model model) {
		super();
		this.model = model;
	}
	
	protected String getAusgabeString() {
		StringBuilder sb = new StringBuilder();
		// TODO implementierung getAusgabeString()
		return sb.toString();
	}
}
