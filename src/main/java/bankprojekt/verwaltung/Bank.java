package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;

import java.util.*;

public class Bank {

    private final long bankleitzahl;
    private Map<Long, Konto> kontenListe;
    private Set<Long> vergebeneKontonummern;
    private static final double STANDARD_DISPO = 1000;


    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
        this.kontenListe = new HashMap<>();
        this.vergebeneKontonummern = new HashSet<>();
    }

    public long getBankleitzahl() {
        return bankleitzahl;
    }

    public Map<Long, Konto> getKontenListe() {
        return kontenListe;
    }

    public long girokontoErstellen(Kunde inhaber) {
        long neueKontennummer = erstelleNeueKontonummer();
        Girokonto girokonto = new Girokonto(inhaber, neueKontennummer, STANDARD_DISPO);
        kontenListe.put(neueKontennummer, girokonto);
        return neueKontennummer;
    }

    public long sparbuchErstellen(Kunde inhaber) {
        long neueKontonummer = erstelleNeueKontonummer();
        Sparbuch sparbuch = new Sparbuch(inhaber, neueKontonummer);
        kontenListe.put(neueKontonummer, sparbuch);
        return neueKontonummer;
    }

    public String getAlleKonten() {
        StringBuilder alleKonten = new StringBuilder();
        for (Konto konto : kontenListe.values()) {
            alleKonten.append(konto).append(System.lineSeparator());
        }
        return String.valueOf(alleKonten);
    }

    public List<Long> getAlleKontonummern() {
        return new ArrayList<>(kontenListe.keySet());
    }

    public boolean geldAbheben(long von, double betrag) throws UngueltigeKontonummerException, GesperrtException, IllegalArgumentException {
        pruefeObKontonummerUngueltig(von);
        return kontenListe.get(von).abheben(betrag);
    }

    public void geldEinzahlen(long von, double betrag) throws UngueltigeKontonummerException, IllegalArgumentException {
        pruefeObKontonummerUngueltig(von);
        kontenListe.get(von).einzahlen(betrag);
    }

    public boolean kontoLoeschen(long nummer) {
        if (kontenListe.containsKey(nummer)) {
            kontenListe.remove(nummer);
            vergebeneKontonummern.remove(nummer);
            return true;
        }
        return false;
    }

    public double getKontostand(long nummer) throws UngueltigeKontonummerException {
        pruefeObKontonummerUngueltig(nummer);
        return kontenListe.get(nummer).getKontostand();
    }

    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws UngueltigeKontonummerException, NichtUeberweisungsfaehigException, GesperrtException {
        pruefeObKontonummerUngueltig(vonKontonr);
        pruefeObKontonummerUngueltig(nachKontonr);
        pruefeObKontoUeberweisungsfähig(kontenListe.get(vonKontonr));
        pruefeObKontoUeberweisungsfähig(kontenListe.get(nachKontonr));

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

    private void pruefeObKontoUeberweisungsfähig(Konto konto) throws NichtUeberweisungsfaehigException {
        if (!(konto instanceof Ueberweisungsfaehig)) {
            throw new NichtUeberweisungsfaehigException(konto.getKontonummer());
        }
    }

    private long erstelleNeueKontonummer() {
        long neueKontonummer = 1;
        while (vergebeneKontonummern.contains(neueKontonummer)) {
            neueKontonummer++;
        }
        vergebeneKontonummern.add(neueKontonummer);
        return neueKontonummer;
    }

    private void pruefeObKontonummerUngueltig(long kontonummer) throws UngueltigeKontonummerException {
        if (!vergebeneKontonummern.contains(kontonummer)) {
            throw new UngueltigeKontonummerException(kontonummer);
        }
    }
}
