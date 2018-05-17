package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Model;

/**
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class AusgabeInDatei extends Ausgabe {

	public AusgabeInDatei(Model model) {
		super(model);
	}
	
	public void schreibeModelInDatei(String path) {
		String outputString = super.getAusgabeString();
		
		File file = new File(path);
		FileWriter writer;
		try {
			writer = new FileWriter(file, false);
		} catch (IOException ex) {
			System.out.println("Fehler beim öffnen/erstellen der Datei!");
			return;
		}
		try {
			writer.write(outputString);
			writer.close();
		} catch (IOException ex) {
			System.out.println("Fehler beim schreiben in die Datei!");
			ex.printStackTrace();
		}
	}
	
}
