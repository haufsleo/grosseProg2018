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
		// Prüfe, ob der im Model gekapselte Netzplan keine Zyklen enthällt
		boolean hatKeineZyklen = this.hatKeineZyklen();
		if (!hatKeineZyklen) {
			System.out.println("Zyklen enthalten");
			return;
		}

		// Prüfe, ob der im Model gekapselte Netzplan zusammenhängend ist
		boolean isZusammenhaengend = this.isZusammenhaengend();
		if (!isZusammenhaengend) {
			System.out.println("Nicht zusammenhängend");
			return;
		}

		// Initialisiere das Model
		if (!this.model.isInitialized()) {
			initModel();
		}
	}

	/**
	 * Prüft, ob der im Model gekapselte Netzplan keine Zyklen enthällt
	 * 
	 * @return true, falls der Netzplan im Model keine Zyklen enthällt, sonst true
	 */
	private boolean hatKeineZyklen() {
		ArrayList<Boolean> check = new ArrayList<>();

		/*
		 * Rufe für ausgehend von allen Startknoten die Helpermethode
		 * hatKeineZyklenHelper auf. Falls ein Ergebnis negativ ausfällt wird false
		 * zurückgegeben
		 */
		for (Knoten s : this.model.getStartknoten()) {
			this.validationsListe = new ArrayList<>();
			check.add(hatKeineZyklenHelper(s));
			if (check.contains(Boolean.valueOf(false))) {
				model.setZyklus(this.validationsListe);
				return false;
			}
		}
		return true;
	}

	private boolean hatKeineZyklenHelper(Knoten aktKnoten) {
		if (this.validationsListe.contains(aktKnoten)) {
			this.validationsListe.add(aktKnoten);
			return false;
		}
		this.validationsListe.add(aktKnoten);

		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			
			return this.hatKeineZyklenHelper(nachfolger);
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

	/**
	 * Initialisiert das Model. Dabei werden drei Phasen durchlaufen:
	 * 
	 * 1. Phase: Vorwärtsrechnung Bei gegebenem Anfangstermin werden aufgrund der
	 * angegebenen Dauer eines Vorganges die frühestmöglichen Anfangs- und
	 * Endzeiten eingetragen. Weiterhin lässt sich die Gesamtdauer eines Projekts
	 * bestimmen.
	 * 
	 * 2. Phase: Rückwärtsrechnung: Bei der Rückwärtsrechnung wird ermittelt,
	 * wann die einzelnen Vorgänge spätestens begonnen und fertiggestellt sein
	 * müssen, damit die Gesamtprojektzeit nicht gefährdet ist.
	 * 
	 * 
	 * 3. Phase: Ermittlung der Zeitreserven und des kritischen Pfades: In dieser
	 * Phase wird ermittelt, welche Zeitreserven existieren und welche Vorgänge
	 * besonders problematisch sind (kritischer Vorgang), weil es bei diesen keine
	 * Zeitreserven gibt. Dazu wird für alle Knoten der Gesamtpuffer (GP)
	 * berechnet, sowie der freie Puffer (FP).
	 */
	private void initModel() {
		if (this.model.isInitialized()) {
			return;
		}

		/*
		 * 1. Phase: Vorwärtsrechnung
		 * 
		 * Setze FAZ der Startknoten
		 */
		for (Knoten startK : this.model.getStartknoten()) {
			// Der Startknoten hat als FAZ immer den Wert 0
			startK.setFaz(0);
		}

		// Setze FEZ aller Knoten als FEZ = FAZ + Dauer
		for (Knoten startK : this.model.getStartknoten()) {
			// startK.setFez(startK.getFaz() + startK.getDauer());
			this.setFezAndFaz(startK);
		}

		// /*
		// * Setze FAZ für alle Nachfolger der Startknoten als der größte
		// * (späteste) FEZ der unmittelbaren Vorgänger.
		// */
		// for (Knoten startK : this.model.getStartknoten()) {
		// for (Knoten nachfolger : startK.getNachfolger()) {
		// setFaz(nachfolger);
		// }
		// }

		/*
		 * 2. Phase: Rückwärtsrechnung
		 * 
		 * Bei der Rückwärtsrechnung wird ermittelt, wann die einzelnen Vorgänge
		 * spätestens begonnen und fertiggestellt sein müssen, damit die
		 * Gesamtprojektzeit nicht gefährdet ist.
		 * 
		 * Für den letzten Vorgang ist der früheste Endzeitpunkt (FEZ) auch der
		 * späteste Endzeitpunkt (SEZ), also SEZ = FEZ.
		 */
		for (Knoten endK : this.model.getEndknoten()) {
			endK.setSez(endK.getFez());
		}

		/*
		 * Für den spätesten Anfangszeitpunkt gilt: SAZ = SEZ – Dauer.
		 */
		for (Knoten endKnoten : this.model.getEndknoten()) {
			this.setSazAndSez(endKnoten);
		}

		// /*
		// * Der SAZ eines Vorgangs wird SEZ aller unmittelbarer Vorgänger
		// *
		// * Haben mehrere Vorgänge einen gemeinsamen Vorgänger, so ist dessen SEZ der
		// * früheste (kleinste) SAZ aller Nachfolger.
		// **/
		// for (Knoten endK : this.model.getEndknoten()) {
		// setSez(endK);
		// }

		// 3. Phase: Ermittlung der Zeitreserven
		for (Knoten startK : this.model.getStartknoten()) {
			/*
			 * Berechnung des Gesamtpuffers für jeden Knoten
			 */
			this.setGp(startK);

			/*
			 * Berechnung des freien Puffers
			 */
			this.setFp(startK);
		}

		/*
		 * Bestimmung der kritischen Vorgänges
		 */
		this.setKritischePfade();

		this.model.initialize();
	}

	private void setFezAndFaz(Knoten aktKnoten) {
		// Für den FEZ gilt: FEZ = FAZ + Dauer
		aktKnoten.setFez(aktKnoten.getFaz() + aktKnoten.getDauer());

		// Wenn Endknoten wird FAZ auf den maximalen FEZ der Vorgängerknoten gesetzt
		if (aktKnoten.getNachfolger().size() == 0) {
			aktKnoten.setFaz(this.getMaxFezOfVorgaenger(aktKnoten));
		}

		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			nachfolger.setFaz(this.getMaxFezOfVorgaenger(nachfolger));
			setFezAndFaz(nachfolger);
		}
	}

	/**
	 * Berechnet SAZ für alle weiteren Knoten, ausgehend von einem aktuellen Knoten
	 * 
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 */
	private void setSazAndSez(Knoten aktKnoten) {
		// Wenn aktueller Knoten ein Anfangsknoten ist, so wird Sez als minimaler SAZ
		// der Nachfolger gesetzt
		if (aktKnoten.getVorgaenger().size() == 0) {
			aktKnoten.setSez(this.getMinSazOfNachfolger(aktKnoten));
		}

		// SAZ = SEZ – Dauer.
		aktKnoten.setSaz(aktKnoten.getSez() - aktKnoten.getDauer());

		for (Knoten vorgaenger : aktKnoten.getVorgaenger()) {
			/*
			 * Der SAZ eines Vorgangs wird SEZ aller unmittelbarer Vorgänger
			 * 
			 * Haben mehrere Vorgänge einen gemeinsamen Vorgänger, so ist dessen SEZ der
			 * früheste (kleinste) SAZ aller Nachfolger.
			 */

			vorgaenger.setSez(this.getMinSazOfNachfolger(vorgaenger));
			// Rufe setSazAndSez rekursiv fpr alle vorgänger vom aktuellen Knoten auf
			setSazAndSez(vorgaenger);
		}
	}

	// /**
	// * Setzt FAZ für alle Knoten ausgehend von einem aktuellen Knoten
	// *
	// * @param aktKnoten
	// * aktuell betrachteter Knoten
	// */
	// private void setFaz(Knoten aktKnoten) {
	// /*
	// * Der FEZ eines Vorgängers ist FAZ aller unmittelbar nachfolgenden Knoten.
	// * Münden mehrere Knoten in einen Vorgang, dann ist der FAZ der größte
	// * (späteste) FEZ der unmittelbaren Vorgänger.
	// */
	// aktKnoten.setFaz(this.getMaxFezOfVorgaenger(aktKnoten));
	//
	// // Rufe setFaz für alle nachfolgenden Knoten von aktKnoten auf
	// for (Knoten nachfolger : aktKnoten.getNachfolger()) {
	// setFaz(nachfolger);
	// }
	// }

	/**
	 * Berechnet den Maximalen FEZ aller Vorgänger eines Knoten
	 * 
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 * @return maximalen FEZ aller Vorgänger des Knoten
	 */
	private int getMaxFezOfVorgaenger(Knoten aktKnoten) {
		int max = Integer.MIN_VALUE;
		for (Knoten vorgaenger : aktKnoten.getVorgaenger()) {
			if (vorgaenger.getFez() > max) {
				max = vorgaenger.getFez();
			}
		}
		return max;
	}

	// /**
	// * Berechnet SEZ ausgehend von einem aktuellen Knoten
	// *
	// * @param aktKnoten
	// * aktuell betrachteter Knoten
	// */
	// private void setSez(Knoten aktKnoten) {
	// /*
	// * Der SAZ eines Vorgangs wird SEZ aller unmittelbarer Vorgänger
	// *
	// * Haben mehrere Vorgänge einen gemeinsamen Vorgänger, so ist dessen SEZ der
	// * früheste (kleinste) SAZ aller Nachfolger.
	// **/
	// aktKnoten.setSez(this.getMinSazOfNachfolger(aktKnoten));
	//
	// for (Knoten vorgaenger : aktKnoten.getVorgaenger()) {
	// setSez(vorgaenger);
	// }
	// }

	/**
	 * Berechnet den minimalen SAZ der Nachfolgenden Knoten eines betrachteten
	 * Knoten
	 * 
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 * @return minimaler SAZ der Nachfolgenden Knoten eines betrachteten Knoten
	 */
	private int getMinSazOfNachfolger(Knoten aktKnoten) {
		int min = Integer.MAX_VALUE;
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			if (nachfolger.getSaz() < min) {
				min = nachfolger.getSaz();
			}
		}
		return min;
	}

	/**
	 * Berechnet den GP aller Knoten ausgehend vom aktuell betrachteten Knoten
	 * 
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 */
	private void setGp(Knoten aktKnoten) {
		/*
		 * Berechnung des Gesamtpuffers für jeden Knoten: GP = SAZ – FAZ = SEZ – FEZ
		 */
		aktKnoten.setGp(aktKnoten.getSaz() - aktKnoten.getFaz());
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			setGp(nachfolger);
		}
	}

	/**
	 * Berechnet den FP aller Knoten ausgehend vom aktuell betrachteten Knoten
	 * 
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 */
	private void setFp(Knoten aktKnoten) {
		/*
		 * Für die Berechnung des freien Puffers gilt: FP= (kleinster FAZ der
		 * nachfolgenden Knoten) - FEZ Ist der aktuelle Knoten der Endknoten, so ist der
		 * Freie Puffer 0, da FAZ==FEZ
		 */
		aktKnoten.setFp(this.getMinFazOfNachfolger(aktKnoten) - aktKnoten.getFez());
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			setFp(nachfolger);
		}
	}

	/**
	 * Berechnet den kleinsten FAZ aller Nachfolger eines betrachteten Knoten
	 * 
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 * @return kleinste FAZ aller Nachfolger eines betrachteten Knoten
	 */
	private int getMinFazOfNachfolger(Knoten aktKnoten) {
		int min = Integer.MAX_VALUE;
		if (aktKnoten.getNachfolger().size() == 0) {
			return aktKnoten.getFez();
		}
		for (Knoten nachfolger : aktKnoten.getNachfolger()) {
			if (nachfolger.getFaz() < min) {
				min = nachfolger.getFaz();
			}
		}
		return min;
	}

	/**
	 * Berechnet die Kritischen Pfade eines Netzplans und setzt sie im Model als
	 * kritischePfade
	 */
	private void setKritischePfade() {
		this.model.setKritischePfade(new ArrayList<>());
		/*
		 * Bestimmung der kritischen Vorgänge ausgehend von jedem Startknoten
		 */
		for (Knoten startK : this.model.getStartknoten()) {
			ArrayList<Knoten> pfad = new ArrayList<>();
			setKritischePfadeHelper(pfad, startK);
		}
	}

	/**
	 * Rekursive Hilfsmethode zur Berechnung der Kritischen Pfade nach dem Prinzip
	 * des Backtracking. Fügt bei erreichen des Endknotens den berechneten Pfad zum
	 * kritischePfade-Array im Model hinzu
	 * 
	 * @param pfad
	 *            aktuell berechneter Pfad
	 * @param aktKnoten
	 *            aktuell betrachteter Knoten
	 */
	private void setKritischePfadeHelper(ArrayList<Knoten> pfad, Knoten aktKnoten) {
		/*
		 * Abbruchkriterium:Endknoten ist erreicht
		 */
		if (aktKnoten.getNachfolger().size() == 0) {
			// Füge aktuellen Knoten in pfad ein
			pfad.add(aktKnoten);
			// Erstell Kopie des kritischen Pfades
			@SuppressWarnings("unchecked")
			ArrayList<Knoten> pfadKopie = (ArrayList<Knoten>) pfad.clone();
			// Füge errechneten Kritischen Pfad zu den im Model gekapselten Kritischen
			// Pfaden hinzu
			model.getKritischePfade().add(pfadKopie);
			// Breche die Mathode ab
			return;
		}
		/*
		 * Bestimmung der kritischen Vorgänge, d.h. GP = 0 und FP = 0
		 */
		if (aktKnoten.getGp() == 0 && aktKnoten.getFp() == 0) {
			// füge aktuellen Knoten zum kritischen Pfad hinzu
			// pfad.add(aktKnoten);
			@SuppressWarnings("unchecked")
			ArrayList<Knoten> pfadKopie = (ArrayList<Knoten>) pfad.clone();
			pfadKopie.add(aktKnoten);
			// Führe für alle Nachfolger rekursiv die Methode setKritischePfadehelper aus
			// und durchlaufe so nach Backtraking den virtuellen Baum
			for (Knoten nachfolger : aktKnoten.getNachfolger()) {
				this.setKritischePfadeHelper(pfadKopie, nachfolger);
			}
			// // Entferne den zuletzt hinzugefügten Knoten aus dem Pfad-Array
			// pfad.remove(pfad.size() - 1);
		}
	}
}
