package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse repräsentiert eine Bank mit Kontenverwaltung
 */
public class Bank {

    private final long bankleitzahl;
    private final Map<Long, Konto> kontenListe;
    private static final double STANDARD_DISPO = 1000;


    /**
     * Erzeugt eine neue Bank mit der angegebenen Bankleitzahl.
     *
     * @param bankleitzahl die Bankleitzahl der Bank
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
        this.kontenListe = new HashMap<>();
    }

    /**
     * Liefert die Bankleitzahl der Bank.
     *
     * @return die Bankleitzahl der Bank
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    /**
     * Liefert die Liste aller Konten der Bank.
     *
     * @return die Liste aller Konten der Bank
     */
    Map<Long, Konto> getKontenListe() {
        return kontenListe;
    }
    //TODO: private setzen

    /**
     * Erstellt ein neues Girokonto für den angegebenen Kunden.
     *
     * @param inhaber der Inhaber des Kontos
     * @return die neu vergebene Kontonummer des Girokontos zurück
     * @throws IllegalArgumentException wenn der Inhaber null ist oder der angegebene Dispo negativ, bzw. NaN ist
     */
    public long girokontoErstellen(Kunde inhaber) throws IllegalArgumentException {
        long neueKontennummer = erstelleNeueKontonummer();
        Girokonto girokonto = new Girokonto(inhaber, neueKontennummer, STANDARD_DISPO);
        kontenListe.put(neueKontennummer, girokonto);
        return neueKontennummer;
    }

    /**
     * Erstellt ein neues Sparbuch für den angegebenen Kunden.
     *
     * @param inhaber der Inhaber des Kontos
     * @return die Kontonummer des neuen Sparbuchs
     * @throws IllegalArgumentException wenn der Inhaber null ist oder der angegebene Dispo negativ, bzw. NaN ist
     */
    public long sparbuchErstellen(Kunde inhaber) throws IllegalArgumentException {
        long neueKontonummer = erstelleNeueKontonummer();
        Sparbuch sparbuch = new Sparbuch(inhaber, neueKontonummer);
        kontenListe.put(neueKontonummer, sparbuch);
        return neueKontonummer;
    }


    /**
     * Erstellt einen String von allen Kontonummern und den zugehörigen Kontoständen.
     *
     * @return alle Kontonummern und Kontostände der Bank als String
     */
    public String getAlleKonten() {
        StringBuilder alleKonten = new StringBuilder();
        for (Konto konto : kontenListe.values()) {
            alleKonten.append(konto.getKontonummer()).append(" ").append(konto.getKontostand()).append(System.lineSeparator());
        }
        return String.valueOf(alleKonten);
    }

    /**
     * Erstellt eine Liste aller gültigen Kontonummern der Bank.
     *
     * @return die Liste aller gültigen Kontonummern der Bank.
     */
    public List<Long> getAlleKontonummern() {
        return new ArrayList<>(kontenListe.keySet());
    }

    /**
     * Hebt den gewünschten Betrag vom angegebenen Konto ab.
     *
     * @param von    Kontonummer des Kontos von welchem das Geld abgehoben werden soll
     * @param betrag Betrag welcher abgehoben werden soll
     * @return true, wenn die Abhebung geklappt hat, sonst false
     * @throws UngueltigeKontonummerException wenn die Kontonummer ungültig ist
     * @throws GesperrtException              wenn das Konto gesperrt ist
     * @throws IllegalArgumentException       wenn der Betrag negativ oder unendlich oder NaN ist
     */
    public boolean geldAbheben(long von, double betrag) throws UngueltigeKontonummerException, GesperrtException, IllegalArgumentException {
        pruefeObKontonummerUngueltig(von);
        return kontenListe.get(von).abheben(betrag);
    }

    /**
     * Zahlt den gewünschten Betrag auf das angegebene Konto ein.
     *
     * @param auf    Kontonummer des Kontos auf welches das Geld eingezahlt werden soll
     * @param betrag Betrag welcher eingezahlt werden soll
     * @throws UngueltigeKontonummerException wenn die Kontonummer ungültig ist
     * @throws IllegalArgumentException       wenn der Betrag negativ oder unendlich oder NaN ist
     */
    public void geldEinzahlen(long auf, double betrag) throws UngueltigeKontonummerException, IllegalArgumentException {
        pruefeObKontonummerUngueltig(auf);
        kontenListe.get(auf).einzahlen(betrag);
    }

    /**
     * Löscht das Konto mit der angegebenen Kontonummer.
     *
     * @param nummer Kontonummer des Kontos, welches gelöscht werden soll
     * @return true, wenn das Konto erfolgreich gelöscht wurde, sonst false
     */
    public boolean kontoLoeschen(long nummer) {
        if (kontenListe.containsKey(nummer)) {
            kontenListe.remove(nummer);
            return true;
        }
        return false;
    }

    /**
     * Liefert den Kontostand des Kontos mit der angegebenen Kontonummer zurück.
     *
     * @param nummer Kontonummer des Kontos dessen Kontostand zurückgegeben werden soll
     * @return Kontostand des Kontos
     * @throws UngueltigeKontonummerException wenn die Kontonummer ungültig ist
     */
    public double getKontostand(long nummer) throws UngueltigeKontonummerException {
        pruefeObKontonummerUngueltig(nummer);
        return kontenListe.get(nummer).getKontostand();
    }

    /**
     * Überweist einen gewünschten Betrag von einem überweisungsfähigen Konto zu einem anderen überweisungsfähigen Konto.
     *
     * @param vonKontonr       Kontonummer des Kontos, von welchem die Überweisung getätigt werden soll
     * @param nachKontonr      Kontonummer des Kontos, welches die Überweisung erhalten soll
     * @param betrag           Überweisungsbetrag
     * @param verwendungszweck Verwendungszweck
     * @return true, wenn die Überweisung durchgeführt wurde, sonst false
     * @throws UngueltigeKontonummerException    wenn die Kontonummer ungültig ist
     * @throws NichtUeberweisungsfaehigException wenn das Konto nicht überweisungsfähig ist
     * @throws GesperrtException                 wenn das Konto gesperrt ist
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws UngueltigeKontonummerException, NichtUeberweisungsfaehigException, GesperrtException {
        pruefeObKontonummerUngueltig(vonKontonr);
        pruefeObKontonummerUngueltig(nachKontonr);
        pruefeObKontoUeberweisungsfaehig(kontenListe.get(vonKontonr));
        pruefeObKontoUeberweisungsfaehig(kontenListe.get(nachKontonr));

        Konto vonKonto = kontenListe.get(vonKontonr);
        Konto nachKonto = kontenListe.get(nachKontonr);

        boolean check;
        check = ((Ueberweisungsfaehig) vonKonto).ueberweisungAbsenden(betrag, nachKonto.getInhaber().getName(), nachKontonr, this.bankleitzahl, verwendungszweck);

        if (check) {
            ((Ueberweisungsfaehig) nachKonto).ueberweisungEmpfangen(betrag, vonKonto.getInhaber().getName(), vonKontonr, this.bankleitzahl, verwendungszweck);
            return true;
        }
        return false;
    }

    private void pruefeObKontoUeberweisungsfaehig(Konto konto) throws NichtUeberweisungsfaehigException {
        if (!(konto instanceof Ueberweisungsfaehig)) {
            throw new NichtUeberweisungsfaehigException(konto.getKontonummer());
        }
    }

    private long erstelleNeueKontonummer() {
        long neueKontonummer = 1;
        while (kontenListe.containsKey(neueKontonummer)) {
            neueKontonummer++;
        }
        return neueKontonummer;
    }

    private void pruefeObKontonummerUngueltig(long kontonummer) throws UngueltigeKontonummerException {
        if (!kontenListe.containsKey(kontonummer)) {
            throw new UngueltigeKontonummerException(kontonummer);
        }
    }
}