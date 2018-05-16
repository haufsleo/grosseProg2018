package controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import model.Knoten;
import model.Model;

public class ControllerTest {
	@Test
	public void hatKeineZyklen_ModelOhneZyklen_RueckgabeTrue() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, new ArrayList<>(), ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, new ArrayList<>());
		knotenliste.add(zweiterKnoten);
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean keineZyklen = controller.hatKeineZyklen();

		// Auswerten
		assertEquals(true, keineZyklen);
	}

	@Test
	public void hatKeineZyklen_ZweiterKnotenHatErstenKnotenAlsNachfolger_RueckgabeFalse() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, new ArrayList<>(), ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		zweiterKnotenNachfolger.add(1);
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean keineZyklen = controller.hatKeineZyklen();

		// Auswerten
		assertEquals(false, keineZyklen);
	}

	@Test
	public void hatKeineZyklen_ErsterKnotenHatZweitenKnotenAlsVorgaengerSowieNachfolgerUndZweiterKnotenHatErstenKnotenAlsVorgaenger_RueckgabeTrueDaKeinExistierenderStartpunkt() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ersterKnotenVorgaenger.add(2);
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean keineZyklen = controller.hatKeineZyklen();

		// Auswerten
		assertEquals(true, keineZyklen);
	}

	@Test
	public void hatKeineZyklen_ZweiKnotenHabenSichGegenseitigAlsNachfolgerSowieVorgaenger_RueckgabeTrueDaKeinExistierenderStartpunkt() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ersterKnotenVorgaenger.add(2);
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		zweiterKnotenNachfolger.add(1);
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean keineZyklen = controller.hatKeineZyklen();

		// Auswerten
		assertEquals(true, keineZyklen);
	}

	@Test
	public void hatKeineZyklen_DritterKnotenHatZweitenKnotenAlsNachfolger_RueckgabeFalse() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		zweiterKnotenNachfolger.add(3);
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		ArrayList<Integer> dritterKnotenVorgaenger = new ArrayList<Integer>();
		dritterKnotenVorgaenger.add(2);
		ArrayList<Integer> dritterKnotenNachfolger = new ArrayList<Integer>();
		dritterKnotenNachfolger.add(2);
		Knoten dritterKnoten = new Knoten(3, "Dritter Schritt", 10, dritterKnotenVorgaenger, dritterKnotenNachfolger);
		knotenliste.add(dritterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean keineZyklen = controller.hatKeineZyklen();

		// Auswerten
		assertEquals(false, keineZyklen);
	}

	@Test
	public void isZusammenhaengend_ZusammenhaengendeKnoten_RueckgabeTrue() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		zweiterKnotenNachfolger.add(3);
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		ArrayList<Integer> dritterKnotenVorgaenger = new ArrayList<Integer>();
		dritterKnotenVorgaenger.add(2);
		ArrayList<Integer> dritterKnotenNachfolger = new ArrayList<Integer>();
		Knoten dritterKnoten = new Knoten(3, "Dritter Schritt", 10, dritterKnotenVorgaenger, dritterKnotenNachfolger);
		knotenliste.add(dritterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean zusammenhaengend = controller.isZusammenhaengend();

		// Auswerten
		assertEquals(true, zusammenhaengend);
	}

	@Test
	public void isZusammenhaengend_DritterKnotenHatEinenVorgaengerAberDieserKeinenNachfolger_RueckgabeFalse() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		ArrayList<Integer> dritterKnotenVorgaenger = new ArrayList<Integer>();
		dritterKnotenVorgaenger.add(2);
		ArrayList<Integer> dritterKnotenNachfolger = new ArrayList<Integer>();
		Knoten dritterKnoten = new Knoten(3, "Dritter Schritt", 10, dritterKnotenVorgaenger, dritterKnotenNachfolger);
		knotenliste.add(dritterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean zusammenhaengend = controller.isZusammenhaengend();

		// Auswerten
		assertEquals(false, zusammenhaengend);
	}

	@Test
	public void hatGueltigeReferenzen_dreiKnotenMitFehlenderReferenzVomZweitenZumDrittenKnoten_nichtGueltig() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		ArrayList<Integer> dritterKnotenVorgaenger = new ArrayList<Integer>();
		dritterKnotenVorgaenger.add(2);
		ArrayList<Integer> dritterKnotenNachfolger = new ArrayList<Integer>();
		Knoten dritterKnoten = new Knoten(3, "Dritter Schritt", 10, dritterKnotenVorgaenger, dritterKnotenNachfolger);
		knotenliste.add(dritterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean gueltig = controller.hatGueltigeReferenzen();

		// Auswerten
		assertEquals(false, gueltig);
	}

	@Test
	public void hatGueltigeReferenzen_dreiKnotenMitKorrektGesetztenReferenzen_istGueltig() {
		// Arrangieren
		ArrayList<Knoten> knotenliste = new ArrayList<Knoten>();
		//
		ArrayList<Integer> ersterKnotenVorgaenger = new ArrayList<Integer>();
		ArrayList<Integer> ersterKnotenNachfolger = new ArrayList<Integer>();
		ersterKnotenNachfolger.add(2);
		Knoten ersterKnoten = new Knoten(1, "Erster Schritt", 10, ersterKnotenVorgaenger, ersterKnotenNachfolger);
		knotenliste.add(ersterKnoten);
		//
		ArrayList<Integer> zweiterKnotenVorgaenger = new ArrayList<Integer>();
		zweiterKnotenVorgaenger.add(1);
		ArrayList<Integer> zweiterKnotenNachfolger = new ArrayList<Integer>();
		zweiterKnotenNachfolger.add(3);
		Knoten zweiterKnoten = new Knoten(2, "Zweiter Schritt", 10, zweiterKnotenVorgaenger, zweiterKnotenNachfolger);
		knotenliste.add(zweiterKnoten);
		//
		ArrayList<Integer> dritterKnotenVorgaenger = new ArrayList<Integer>();
		dritterKnotenVorgaenger.add(2);
		ArrayList<Integer> dritterKnotenNachfolger = new ArrayList<Integer>();
		Knoten dritterKnoten = new Knoten(3, "Dritter Schritt", 10, dritterKnotenVorgaenger, dritterKnotenNachfolger);
		knotenliste.add(dritterKnoten);
		//
		Model model = new Model(knotenliste, "Testliste");
		//
		Controller controller = new Controller(model);

		// Ausführen
		boolean gueltig = controller.hatGueltigeReferenzen();

		// Auswerten
		assertEquals(true, gueltig);
	}
}
