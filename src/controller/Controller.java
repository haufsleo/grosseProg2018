package controller;

import java.util.ArrayList;

import model.Knoten;
import model.Model;

/**
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class Controller {
	private Model model;
	private ArrayList<Knoten> validationsListe;

	// Konstruktor
	public Controller(Model model) {
		super();
		this.model = model;
	}

	/**
	 * 
	 */
	public void calculate() {
		boolean hatKeineZyklen = this.hatKeineZyklen();
		boolean isZusammenhaengend = this.isZusammenhaengend();

		if (!hatKeineZyklen) {
			System.out.println("Zyklen enthalten");
			return;
		}
		if (!isZusammenhaengend) {
			System.out.println("Nicht zusammenhängend");
			return;
		}

		if (!this.model.isInitialized()) {
			initModel();
		}

		this.setKritischePfade();

	}

	private boolean hatKeineZyklen() {

		ArrayList<Boolean> check = new ArrayList<>();

		for (Knoten s : this.model.getStartknoten()) {
			this.validationsListe = new ArrayList<>();
			check.add(hatKeineZyklenHelper(s));
			if (check.contains(false)) {
				return false;
			}
		}
		return true;
	}

	private boolean hatKeineZyklenHelper(Knoten aktKnoten) {
		if (this.validationsListe.contains(aktKnoten)) {
			return false;
		}
		if (aktKnoten.getNachfolger().size() > 0) {
			ArrayList<Boolean> check = new ArrayList<>();
			for (Knoten nachfolger : aktKnoten.getNachfolger()) {
				check.add(this.hatKeineZyklenHelper(nachfolger));
				if (check.contains(false)) {
					return false;
				}
			}
		} else {
			return true;
		}
		return true;
	}

	private boolean isZusammenhaengend() {
		this.validationsListe = new ArrayList<>();

		for (Knoten startK : this.model.getStartknoten()) {
			isZusammenhaengendHelper(startK);
		}
		if (this.validationsListe.size() == model.getKnoten().size()) {
			return true;
		} else {
			return false;
		}
	}

	private void isZusammenhaengendHelper(Knoten aktKnoten) {
		if (!this.validationsListe.contains(aktKnoten)) {
			this.validationsListe.add(aktKnoten);
		}
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			isZusammenhaengendHelper(nachfolger);
		}
	}

	// Hilfsmethoden
	private void initModel() {
		if (this.model.isInitialized()) {
			return;
		}

		for (Knoten startK : this.model.getStartknoten()) {
			startK.setFaz(0);
			startK.setFez(startK.getFaz() + startK.getDauer());
		}
		for (Knoten startK : this.model.getStartknoten()) {
			for (Knoten nachfolger : startK.getNachfolger()) {
				setFez(nachfolger);
			}
		}

		for (Knoten endK : this.model.getEndknoten()) {
			endK.setSez(endK.getFez());
		}
		for (Knoten endK : this.model.getEndknoten()) {
			for (Knoten vorgaenger : endK.getVorgaenger()) {
				setSez(vorgaenger);
			}
		}

		for (Knoten startK : this.model.getStartknoten()) {
			this.setGp(startK);
			this.setFp(startK);
		}

		this.model.initialize();
	}

	private void setFez(Knoten aktKnoten) {
		aktKnoten.setFez(this.getMaxFezOfVorgaenger(aktKnoten));

		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			setFez(nachfolger);
		}
	}

	private int getMaxFezOfVorgaenger(Knoten aktKnoten) {
		int max = Integer.MAX_VALUE;
		for (Knoten v : aktKnoten.getVorgaenger()) {
			if (v.getFez() > max) {
				max = v.getFez();
			}
		}
		return max;
	}

	private void setSez(Knoten aktKnoten) {
		aktKnoten.setSaz(this.getMinSazOfNachfolger(aktKnoten));

		for (Knoten vorgaenger : aktKnoten.getVorgaenger()) {
			setSez(vorgaenger);
		}
	}

	private int getMinSazOfNachfolger(Knoten aktKnoten) {
		int min = Integer.MAX_VALUE;
		for (Knoten n : aktKnoten.getVorgaenger()) {
			if (n.getSaz() < min) {
				min = n.getSaz();
			}
		}
		return min;
	}

	private void setGp(Knoten aktKnoten) {
		aktKnoten.setGp(aktKnoten.getSaz() - aktKnoten.getFaz());
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			setGp(nachfolger);
		}
	}

	private void setFp(Knoten aktKnoten) {
		aktKnoten.setFp(this.getKleinstesFazDerNachfolger(aktKnoten) - aktKnoten.getFez());
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			setFp(nachfolger);
		}
	}

	private int getKleinstesFazDerNachfolger(Knoten aktKnoten) {
		int min = Integer.MAX_VALUE;
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			if (nachfolger.getFaz() < min) {
				min = nachfolger.getFaz();
			}
		}
		return min;
	}

	private void setKritischePfade() {
		for (Knoten startK : this.model.getStartknoten()) {
			ArrayList<Knoten> pfad = new ArrayList<>();
			setKritischePfadehelper(pfad, startK);
		}
	}

	private void setKritischePfadehelper(ArrayList<Knoten> pfad, Knoten aktKnoten) {
		if (aktKnoten.getNachfolger().size() == 0) {
			// Endknoten erreicht
			@SuppressWarnings("unchecked")
			ArrayList<Knoten> pfadKopie = (ArrayList<Knoten>) pfad.clone();
			model.getKritischePfade().add(pfadKopie);
			return;
		}

		if (aktKnoten.getGp() == 0 && aktKnoten.getFp() == 0) {
			pfad.add(aktKnoten);
			for (Knoten nachfolger : aktKnoten.getNachfolger()) {
				this.setKritischePfadehelper(pfad, nachfolger);
			}
			pfad.remove(pfad.size() - 1);
		}
	}
}
