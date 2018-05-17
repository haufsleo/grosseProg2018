package main;

import java.io.File;
import java.io.IOException;

import controller.Controller;
import io.AusgabeInDatei;
import io.LeseAusDatei;
import model.Model;

/**
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class Main {
	public static void main(String[] args) {
		String dateiendung;
		String verzeichnis;

		// Nur zum Testen- wird später aus über die Argumente args der Jar
		// übergeben
		args = new String[2];
		args[0] = ".in";
		args[1] = "/Users/hfs23/Dropbox/MATSE/Programmieraufgaben/2018_GrosseProg/Testfaelle";

		// Parameterübergabe prüfen
		if (args.length != 2) {
			// keine korrekte Parameterübergabe
			System.out.println(
					"Es müssen 2 Parameter übergeben werden. Paramter 1: Endung der Eingabedateien (z.B.: .in)\nParameter 2: Verzechnis aus dem die Eingabedateien gelesen werden soll.");
			return;
		}
		dateiendung = args[0];
		verzeichnis = args[1];

		File f;
		try {
			try {
				f = new File(verzeichnis);
			} catch (Exception ex) {
				throw new IOException("Der Angegebene Pfad existiert nicht");
			}

			if (f.isDirectory() && f.canRead()) {
				File[] dateien = f.listFiles();
				for (int i = 0; i < dateien.length; i++) {
					// Prüfe ob die Datei gelesen werden kann
					if (dateien[i].isFile() && dateien[i].canRead()) {
						String tempEndung = dateien[i].getName().substring(dateien[i].getName().lastIndexOf("."),
								dateien[i].getName().length());
						// wenn die Dateiendung der gewählten entspricht
						// wird die Datei eingelesen
						if (dateiendung.equals(tempEndung)) {

							// Eingabe

							LeseAusDatei in = new LeseAusDatei();
							Model model = in.getModelAusDatei(dateien[i]);

							// Berechnung
							Controller c = new Controller(model);
							c.calculate();

							// Ausgabe
							AusgabeInDatei out = new AusgabeInDatei(model);

							String outputPath = verzeichnis + "/" + (dateien[i].getName().replace(dateiendung, ".out"));
							out.schreibeModelInDatei(outputPath);

							// OutputConsole out = new OutputConsole();
							// out.printEntireOutputString(model);
						}
					}
				}
				System.out.println(args[1] + ": Vorgang abgeschlossen.");
			} else {
				throw new IOException("Der Angegebene Pfad ist kein Ordner oder kann nicht geöffnet werden.");
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
