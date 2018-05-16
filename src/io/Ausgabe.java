package io;

import java.util.ArrayList;

import model.Knoten;
import model.Model;

/**
 * Ermöglicht zu einem Model die Ausgabe der kenngrößen und kritischen Pfade
 * auszugeben
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public abstract class Ausgabe {
	private Model model;

	/**
	 * Konstruktor, der Ausgabe mit einem Model initialisiert
	 * 
	 * @param model
	 *            model, welches die auszugebenen Daten enthällt
	 */
	public Ausgabe(Model model) {
		super();
		this.model = model;
	}

	/**
	 * Gibt den Ausgabestring zurück.
	 * 
	 * Falls nicht zusammenhängend oder falls Zyklen enthalten sind, wird ein
	 * entsprechender Fehler ausgegeben.
	 * 
	 * @return Ausgabestring
	 */
	protected String getAusgabeString() {
		StringBuilder sb = new StringBuilder();

		if (this.model.getKnoten().size() == 0) {
			sb.append("Berechnung nicht möglich.");
			sb.append("\n");
			sb.append("Bitte sehen Sie sich die Konsolenausgabe an, um weitere Informationen zu erhalten.");
		} else if (this.model.getZyklus().size() != 0) {
			sb.append(this.model.getName());
			sb.append("\n");
			sb.append("\n");
			sb.append("Berechnung nicht möglich.");
			sb.append("\n");
			sb.append("Zyklus erkannt: ");
			this.getZyklusString(sb);
		} else if (!this.model.isZusammenhaengend()) {
			sb.append(this.model.getName());
			sb.append("\n");
			sb.append("\n");
			sb.append("Berechnung nicht möglich.");
			sb.append("\n");
			sb.append("Nicht zusammenhängend.");
		} else if (!this.model.isGueltigeReferenzen()) {
			sb.append(this.model.getName());
			sb.append("\n");
			sb.append("\n");
			sb.append("Berechnung nicht möglich.");
			sb.append("\n");
			sb.append(
					"Referenzen der Eingabe sind nicht gültig! Es gibt also mindestens einen Knoten,\ndessen Nachfolger den Knoten selbst nicht als Vorgänger hat\nbzw. dessen Vorgänger den Knoten selbst nicht als Nachfolger hat.");
		} else {
			sb.append("Vorgangsnummer; Vorgangsbeschreibung; D; FAZ; FEZ; SAZ; SEZ; GP; FP");
			sb.append("\n");
			this.getKnotenbeschreibung(sb);
			sb.append("\n");
			this.getVorgangString(sb);
			sb.append("\n");
			this.getGesamtdauer(sb);
			sb.append("\n");
			sb.append("\n");
			this.getKritischerPfadString(sb);
		}

		return sb.toString();
	}

	/**
	 * Gibt die Beschreibung eines Knotens im Netzplan. Dabei wird der übergebene
	 * StringBuilder verändert.
	 * 
	 * @param sb
	 *            Stringbuilder, an den die Beschreibung angehängt werden soll
	 */
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

	/**
	 * Gibt die Beschreibung von Anfangs- und Endvorgang zurück
	 * 
	 * @param sb
	 *            Stringbuilder, an den die Beschreibung von Anfangs- und Endvorgang
	 *            angehängt werden soll
	 */
	private void getVorgangString(StringBuilder sb) {
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

	/**
	 * Gibt die Gesamtdauer des kritischen Pfades zurück. Sind mehrere Kritische
	 * Pfade enthalten, so wird "Nicht eindeutig" zurückgegeben
	 * 
	 * @return Gesamtdauer des kritischen Pfades. Sind mehrere Kritische Pfade
	 *         enthalten, so wird "Nicht eindeutig" zurückgegeben
	 */
	private void getGesamtdauer(StringBuilder sb) {
		sb.append("Gesamtdauer: ");
		if (this.model.getKritischePfade().size() == 0) {
			sb.append(0);
		} else if (this.model.getKritischePfade().size() > 1) {
			sb.append("Nicht eindeutig");
		} else {
			int gesamtdauer = 0;
			ArrayList<Knoten> firstKritPfad = this.model.getKritischePfade().get(0);
			for (Knoten knoten : firstKritPfad) {
				gesamtdauer += knoten.getDauer();
			}
			sb.append(gesamtdauer);
		}

	}

	/**
	 * Hängt die String- Repräsentation des/der Kritischen Pfade(s) an einen
	 * übergebenen Stringbuilder an
	 * 
	 * @param sb
	 *            StringBuilder, an den die String- Repräsentation des/der
	 *            Kritischen Pfade(s) angehängt werden soll
	 */
	private void getKritischerPfadString(StringBuilder sb) {
		if (this.model.getKritischePfade().size() > 1) {
			sb.append("Kritische Pfade");
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

	/**
	 * Hängt die String- Repräsentation eines Zyklus an einen übergebenen
	 * Stringbuilder an
	 * 
	 * @param sb
	 *            StringBuilder, an den die String- Repräsentation des/der Zyklus
	 *            angehängt werden soll
	 */
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

	/**
	 * Gibt die Position des ersten Elementes in einer ArrayList von Knoten zurück,
	 * die doppelt vorkommt
	 * 
	 * @param knoten
	 *            ArrayList<Knoten>, die überprüft werden soll
	 * @return Position des ersten Elementes in einer ArrayList von Knoten, die
	 *         doppelt vorkommt
	 */
	private int posDerErstenWiederholung(ArrayList<Knoten> knoten) {
		ArrayList<Knoten> ks = new ArrayList<>();
		for (int i = 0; i < knoten.size(); i++) {
			Knoten k = knoten.get(i);
			if (ks.contains(k)) {
				return ks.indexOf(k);
			}
			ks.add(k);
		}
		return 0;
	}

}
