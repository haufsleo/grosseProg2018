package model;


import java.util.ArrayList;

/**
 * 
 * @author M. Leonard Haufs Prüflingsnummer: 101-20540
 *
 */
public class Knoten {
	
	private int vorgangsnummer;
	private String vorgangsbezeichnung;
	
	private int faz;
	private int fez;
	private int dauer;
	private int gp;
	private int fp;
	private int saz;
	private int sez;
	
	ArrayList<Knoten> vorgaenger;
	ArrayList<Integer> vorgaengerNummern;
	
	ArrayList<Knoten> nachfolger;
	ArrayList<Integer> nachfolgerNummern;
	
	// Getter und Setter
	public int getVorgangsnummer() {
		return vorgangsnummer;
	}
	public void setVorgangsnummer(int vorgangsnummer) {
		this.vorgangsnummer = vorgangsnummer;
	}
	public String getVorgangsbezeichnung() {
		return vorgangsbezeichnung;
	}
	public void setVorgangsbezeichnung(String vorgangsbezeichnung) {
		this.vorgangsbezeichnung = vorgangsbezeichnung;
	}
	public int getFaz() {
		return faz;
	}
	public void setFaz(int faz) {
		this.faz = faz;
	}
	public int getFez() {
		return fez;
	}
	public void setFez(int fez) {
		this.fez = fez;
	}
	public int getDauer() {
		return dauer;
	}
	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	public int getGp() {
		return gp;
	}
	public void setGp(int gp) {
		this.gp = gp;
	}
	public int getFp() {
		return fp;
	}
	public void setFp(int fp) {
		this.fp = fp;
	}
	public int getSaz() {
		return saz;
	}
	public void setSaz(int saz) {
		this.saz = saz;
	}
	public int getSez() {
		return sez;
	}
	public void setSez(int sez) {
		this.sez = sez;
	}
	public ArrayList<Knoten> getVorgaenger() {
		return vorgaenger;
	}
	public void setVorgaenger(ArrayList<Knoten> vorgaenger) {
		this.vorgaenger = vorgaenger;
	}
	public ArrayList<Integer> getVorgaengerNummern() {
		return vorgaengerNummern;
	}
	public void setVorgaengerNummern(ArrayList<Integer> vorgaengerNummern) {
		this.vorgaengerNummern = vorgaengerNummern;
	}
	public ArrayList<Knoten> getNachfolger() {
		return nachfolger;
	}
	public void setNachfolger(ArrayList<Knoten> nachfolger) {
		this.nachfolger = nachfolger;
	}
	public ArrayList<Integer> getNachfolgerNummern() {
		return nachfolgerNummern;
	}
	public void setNachfolgerNummern(ArrayList<Integer> nachfolgerNummern) {
		this.nachfolgerNummern = nachfolgerNummern;
	}
		
	// Konstruktor
	public Knoten(int vorgangsnummer, String vorgangsbezeichnung, int dauer, ArrayList<Integer> vorgaengerNummern, ArrayList<Integer> nachfolgerNummern) {
		super();

		this.vorgangsnummer = vorgangsnummer;
		this.vorgangsbezeichnung = vorgangsbezeichnung;
		this.dauer = dauer;		
		this.vorgaengerNummern = vorgaengerNummern;
		this.nachfolgerNummern = nachfolgerNummern;
		
		this.vorgaenger = new ArrayList<>();
		this.nachfolger = new ArrayList<>();
	}
}
