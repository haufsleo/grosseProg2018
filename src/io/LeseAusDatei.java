package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Knoten;
import model.Model;

/**
 * Ermöglicht das Einlesen der Daten eines Models aus einer Datei
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class LeseAusDatei {

	/**
	 * Liefert die Daten eines Models, die in einer Datei gespeichert sind.
	 * 
	 * @param file
	 *            Datei, aus der gelesen werden soll.
	 * @return Model mit dem gekapselten Daten. Falls eine ungültige Eingabe
	 *         erfolgt, wird ein leeres Model zurückgegeben.
	 */
	public Model getModelAusDatei(File file) {
		ArrayList<Knoten> knoten = new ArrayList<>();
		String kommentar = "Fehler beim Einlesen.";
		ArrayList<Integer> vorgangsnummern = new ArrayList<>();

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException ex) {
			System.out.println(ex);
			return new Model();
		}

		try {
			String aktZeile = "";
			while ((aktZeile = br.readLine()) != null) {
				if (aktZeile.startsWith("//")) {
					// Zeile beginnt mit "//+ " ?
					if (aktZeile.startsWith("//+ ")) {
						if (aktZeile.length() > 4) {
							kommentar = aktZeile.substring(4, aktZeile.length());
						}
						continue;
					}
					continue;
				}

				String aktZeileOhneLeer = aktZeile.replace(" ", "");
				String[] zeileSplit = aktZeileOhneLeer.split(";");
				if (zeileSplit.length != 5) {
					System.out.println("In Datei " + file.getName()
							+ ": Ungenügende Eingabe. Es müssen je Zeile genau 5 Argumente getrennt mit einem Semikolon übergeben werden: "
							+ aktZeile);
					br.close();
					return new Model();
				}
				int nr = Integer.parseInt(zeileSplit[0]);
				vorgangsnummern.add(nr);
				String beschr = aktZeile.split("; ")[1];
				int dauer = Integer.parseInt(zeileSplit[2]);

				ArrayList<Integer> vorgaengerNummern = new ArrayList<>();
				if (!zeileSplit[3].equals("-")) {
					String[] vorgaengerNummernArr = zeileSplit[3].split(",");
					for (int i = 0; i < vorgaengerNummernArr.length; i++) {
						String string = vorgaengerNummernArr[i];
						int number = Integer.parseInt(string);
						vorgaengerNummern.add(number);
					}
				}

				ArrayList<Integer> nachfolgerNummern = new ArrayList<>();
				if (!zeileSplit[4].equals("-")) {
					String[] nachfolgerNummernArr = zeileSplit[4].split(",");
					for (int i = 0; i < nachfolgerNummernArr.length; i++) {
						String string = nachfolgerNummernArr[i];
						int number = Integer.parseInt(string);
						nachfolgerNummern.add(number);
					}
				}
				// Prüfe, ob vorgangsnummern nicht doppelt vorliegen
				if (!vorgangsnummernNichtDoppelt(vorgaengerNummern)) {
					System.out.println("In Datei " + file.getName()
							+ ": Ungenügende Eingabe: Es kommt mindestens eine Vorgangsnummer mehrfach vor.");
					br.close();
					return new Model();
				}
				Knoten k = new Knoten(nr, beschr, dauer, vorgaengerNummern, nachfolgerNummern);
				knoten.add(k);
			}
			br.close();
		} catch (IOException ex) {
			System.out.println(ex);
			new Model();
		} catch (NumberFormatException e) {
			System.out.println("In Datei " + file.getName()
					+ ": Ungenügende Eingabe. Es wurde mindestens eine ungültige Zahl eingeben.");
			return new Model();
		}
		if (!alleKnotenVerweisenAufExistierendenKnoten(knoten, vorgangsnummern)) {
			System.out.println("In Datei " + file.getName()
					+ ": Ungenügende Eingabe: Es existieren ungültige Referenzen, da mindestens ein Knoten auf einen nicht existenten Knoten referenziert.");
			return new Model();
		}
		Model model = new Model(knoten, kommentar);
		return model;
	}

	/**
	 * Prüft, ob die Vorgangsnummern nicht doppelt vorliegen
	 * 
	 * @param vorgangsnummern
	 *            die zu Prüfen sind
	 * @return true, falls die Vorgangsnummern nicht doppelt vorliegen
	 */
	private boolean vorgangsnummernNichtDoppelt(ArrayList<Integer> vorgangsnummern) {
		@SuppressWarnings("unchecked")
		ArrayList<Integer> copyOfVorgangsnummern = (ArrayList<Integer>) vorgangsnummern.clone();

		for (int i = 0; i < copyOfVorgangsnummern.size(); i++) {
			Integer vorgangsnummer = copyOfVorgangsnummern.get(i);
			copyOfVorgangsnummern.remove(vorgangsnummer);
			if (copyOfVorgangsnummern.contains(Integer.valueOf(vorgangsnummer))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Prüft, ob alle Knoten auf einen existierenden Knoten verweisen.
	 * 
	 * @param knoten
	 *            Knotenliste, der zu prüfenden Knoten
	 * @param vorgangsnummern
	 *            Liste der Vorgangsnummern aller Knoten
	 * @return true, falls alle Knoten auf einen existierenden Knoten verweisen,
	 *         sonst false
	 */
	private boolean alleKnotenVerweisenAufExistierendenKnoten(ArrayList<Knoten> knoten,
			ArrayList<Integer> vorgangsnummern) {
		for (Knoten k : knoten) {
			for (int nachfolgernummer : k.getNachfolgerNummern()) {
				if (!vorgangsnummern.contains(Integer.valueOf(nachfolgernummer))) {
					return false;
				}
			}

			for (int vorgaengernummer : k.getVorgaengerNummern()) {
				if (!vorgangsnummern.contains(Integer.valueOf(vorgaengernummer))) {
					return false;
				}
			}

		}
		return true;
	}
}
