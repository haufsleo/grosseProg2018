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
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class LeseAusDatei {
	public Model getModelAusDatei(File file) {
		ArrayList<Knoten> knoten = new ArrayList<>();
		String kommentar = "Fehler beim Einlesen.";

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
					if (aktZeile.startsWith("//+")) {
						if (aktZeile.length() > 3) {
							kommentar = aktZeile.substring(3, aktZeile.length());
						}
						continue;
					}
					continue;
				}

				aktZeile = aktZeile.replace(" ", "");
				String[] zeileSplitBySemicolon = aktZeile.split(";");
				if (zeileSplitBySemicolon.length != 5) {
					System.out.println(
							"Ungültige Eingabe. Es müssen je Zeile genau 5 Argumente getrennt mit einem Semikolon übergeben werden: "
									+ aktZeile);
					br.close();
					return new Model();
				}
					int nr = Integer.parseInt(zeileSplitBySemicolon[0]);
					String beschr = zeileSplitBySemicolon[1];
					int dauer = Integer.parseInt(zeileSplitBySemicolon[2]);

					ArrayList<Integer> vorgaengerNummern = new ArrayList<>();					
					if(!zeileSplitBySemicolon[3].equals("-")) {
						String[] vorgaengerNummernArr = zeileSplitBySemicolon[3].split(",");
						for (int i = 0; i < vorgaengerNummernArr.length; i++) {
							String string = vorgaengerNummernArr[i];
							int number = Integer.parseInt(string);
							vorgaengerNummern.add(number);
						}						
					}
					
					ArrayList<Integer> nachfolgerNummern = new ArrayList<>();
					if(!zeileSplitBySemicolon[4].equals("-")) {
						String[] nachfolgerNummernArr = zeileSplitBySemicolon[4].split(",");
						for (int i = 0; i < nachfolgerNummernArr.length; i++) {
							String string = nachfolgerNummernArr[i];
							int number = Integer.parseInt(string);
							nachfolgerNummern.add(number);
						}						
					}
					
					Knoten k = new Knoten(nr, beschr, dauer, vorgaengerNummern, nachfolgerNummern);
					knoten.add(k);
			}
			br.close();
		} catch (IOException ex) {
			System.out.println(ex);
			new Model();
		} catch (NumberFormatException e) {
			System.out.println("ungültige Eingabe. Es wurde ein Buchstabe statt einer erwarteten Zahl eingeben.");
			return new Model();
		}
		
		Model model = new Model(knoten, kommentar); 
		return model;
	}
}
