package model;

import java.util.ArrayList;

/**
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class Model {
	private boolean initialized;

	private ArrayList<Knoten> startknoten;
	private ArrayList<Knoten> endknoten;

	private ArrayList<Knoten> knoten;

	private ArrayList<ArrayList<Knoten>> kritischePfade;
	private ArrayList<Knoten> zyklus;
	private boolean isZusammenhaengend;
	private boolean gueltigeReferenzen;

	private String name;

	public boolean isInitialized() {
		return initialized;
	}

	public void initialize() {
		this.initialized = true;
	}

	public boolean isZusammenhaengend() {
		return isZusammenhaengend;
	}

	public void setZusammenhaengend(boolean isZusammenhaengend) {
		this.isZusammenhaengend = isZusammenhaengend;
	}

	public boolean isGueltigeReferenzen() {
		return gueltigeReferenzen;
	}

	public void setGueltigeReferenzen(boolean gueltigeReferenzen) {
		this.gueltigeReferenzen = gueltigeReferenzen;
	}

	// Getter und Setter
	public ArrayList<ArrayList<Knoten>> getKritischePfade() {
		return kritischePfade;
	}

	public void setKritischePfade(ArrayList<ArrayList<Knoten>> kritischePfade) {
		this.kritischePfade = kritischePfade;
	}

	public ArrayList<Knoten> getZyklus() {
		return zyklus;
	}

	public void setZyklus(ArrayList<Knoten> zyklus) {
		this.zyklus = zyklus;
	}

	public ArrayList<Knoten> getStartknoten() {
		return startknoten;
	}

	public ArrayList<Knoten> getEndknoten() {
		return endknoten;
	}

	public ArrayList<Knoten> getKnoten() {
		return knoten;
	}

	public String getName() {
		return name;
	}

	// Konstruktoren

	public Model() {
		super();
		this.knoten = new ArrayList<>();
		this.name = "not set";

		this.startknoten = new ArrayList<>();
		this.endknoten = new ArrayList<>();

		this.kritischePfade = new ArrayList<>();
		this.zyklus = new ArrayList<>();
		this.gueltigeReferenzen = true;
	}

	public Model(ArrayList<Knoten> knoten, String name) {
		this();
		this.knoten = knoten;
		this.name = name;

		this.initKnoten(knoten);
		this.startknoten = this.getStartknoten(knoten);
		this.endknoten = this.getEndknoten(knoten);
	}

	/**
	 * Bestimmt die Startknoten einer Liste von Knoten
	 * 
	 * @param knoten
	 *            Liste von Knoten
	 * @return Startknoten einer Liste von Knoten
	 */
	private ArrayList<Knoten> getStartknoten(ArrayList<Knoten> knoten) {
		ArrayList<Knoten> startknoten = new ArrayList<>();
		for (Knoten k : knoten) {
			if (k.getVorgaengerNummern().size() == 0) {
				startknoten.add(k);
			}
		}

		return startknoten;
	}

	/**
	 * Bestimmt die Endknoten einer Liste von Knoten
	 * 
	 * @param knoten
	 *            Liste von Knoten
	 * @return Endknoten einer Liste von Knoten
	 */
	private ArrayList<Knoten> getEndknoten(ArrayList<Knoten> knoten) {
		ArrayList<Knoten> endknoten = new ArrayList<>();
		for (Knoten k : knoten) {
			if (k.getNachfolgerNummern().size() == 0) {
				endknoten.add(k);
			}
		}

		return endknoten;
	}

	/**
	 * Initialisiert eine liste von Knoten, setzt also die Vorgänger und Nachfolger
	 * der Knoten
	 * 
	 * @param knoten
	 *            Liste von Knoten
	 */
	private void initKnoten(ArrayList<Knoten> knoten) {
		for (Knoten k : knoten) {
			for (int vorgaengerNr : k.getVorgaengerNummern()) {
				for (Knoten k2 : knoten) {
					if (k2.getVorgangsnummer() == vorgaengerNr) {
						k.getVorgaenger().add(k2);
					}
				}
			}

			for (int nachfolgerNr : k.getNachfolgerNummern()) {
				for (Knoten k2 : knoten) {
					if (k2.getVorgangsnummer() == nachfolgerNr) {
						k.getNachfolger().add(k2);
					}
				}
			}
		}
	}

}
