package io;

import java.util.ArrayList;

import model.Knoten;
import model.Model;

/**
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public abstract class Ausgabe {
	private Model model;

	public Ausgabe(Model model) {
		super();
		this.model = model;
	}

	protected String getAusgabeString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.model.getName());
		sb.append("\n");
		sb.append("\n");

		if (this.model.getZyklus().size() != 0) {
			sb.append("Berechnung nicht möglich.");
			sb.append("Zyklus erkannt: ");
			this.getZyklusString(sb);
		} else {
			sb.append("Vorgangsnummer; Vorgangsbeschreibung; D; FAZ; FEZ; SAZ; SEZ; GP; FP");
			sb.append("\n");
			this.getKnotenbeschreibung(sb);
			sb.append("\n");
			this.getAnfangsvorgangString(sb);
			sb.append("\n");
			sb.append("Gesamtdauer: ");
			sb.append(this.getGesamtdauer());
			sb.append("\n");
			sb.append("\n");
			this.getKritischerPfadString(sb);
		}

		return sb.toString();
	}

	private void getKnotenbeschreibung(StringBuilder sb) {
		for (Knoten knoten : model.getKnoten()) {
			sb.append(knoten.getVorgangsnummer());
			sb.append("; ");
			sb.append(knoten.getVorgangsbezeichnung());
			sb.append("; ");
			sb.append(knoten.getDauer());
			sb.append("; ");
			sb.append(knoten.getFaz());
			sb.append("; ");
			sb.append(knoten.getFez());
			sb.append("; ");
			sb.append(knoten.getSaz());
			sb.append("; ");
			sb.append(knoten.getSez());
			sb.append("; ");
			sb.append(knoten.getGp());
			sb.append("; ");
			sb.append(knoten.getFp());
			sb.append("\n");
		}
	}

	private void getAnfangsvorgangString(StringBuilder sb) {
		sb.append("Anfangsvorgang: ");
		for (int i = 0; i < model.getStartknoten().size(); i++) {
			Knoten startK = model.getStartknoten().get(i);

			sb.append(startK.getVorgangsnummer());
			if (i != model.getStartknoten().size() - 1) {
				sb.append(",");
			}
		}
		sb.append("\n");
		sb.append("Endvorgang: ");
		for (int i = 0; i < model.getEndknoten().size(); i++) {
			Knoten endK = model.getEndknoten().get(i);

			sb.append(endK.getVorgangsnummer());
			if (i != model.getEndknoten().size() - 1) {
				sb.append(",");
			}
		}
	}

	private String getGesamtdauer() {
		if (this.model.getKritischePfade().size() == 0) {
			return 0 + "";
		}
		if (this.model.getKritischePfade().size() > 1) {
			return "Nicht eindeutig";
		}

		int gesamtdauer = 0;
		ArrayList<Knoten> firstKritPfad = this.model.getKritischePfade().get(0);
		for (Knoten knoten : firstKritPfad) {
			gesamtdauer += knoten.getDauer();
		}
		return gesamtdauer + "";
	}

	private void getKritischerPfadString(StringBuilder sb) {
		if (this.model.getKritischePfade().size() > 1) {
			sb.append("Kritische(r) Pfad");
		} else {
			sb.append("Kritischer Pfad");
		}
		sb.append("\n");

		for (ArrayList<Knoten> kritischerPfad : this.model.getKritischePfade()) {
			for (int i = 0; i < kritischerPfad.size(); i++) {
				Knoten knoten = kritischerPfad.get(i);
				sb.append(knoten.getVorgangsnummer());
				if (i != kritischerPfad.size() - 1) {
					sb.append("->");
				}
			}
			sb.append("\n");
		}
	}
	
	private void getZyklusString(StringBuilder sb) {
		int posDerErstenWiederholung = this.posDerErstenWiederholung(this.model.getZyklus());
		
		for (int i = posDerErstenWiederholung; i < this.model.getZyklus().size(); i++) {
			Knoten knoten = this.model.getZyklus().get(i);
			sb.append(knoten.getVorgangsnummer());
			if (i != this.model.getZyklus().size() - 1) {
				sb.append("->");
			}
		}
		sb.append("\n");
	}
	

	private int posDerErstenWiederholung(ArrayList<Knoten> knoten) {
		ArrayList<Knoten> ks= new ArrayList<>();
		for (int i = 0; i < knoten.size(); i++) {
			Knoten k = knoten.get(i);
			if(ks.contains(k)) {
				return ks.indexOf(k);
			}
			ks.add(k);
		}
		return 0;
	}
	
}
