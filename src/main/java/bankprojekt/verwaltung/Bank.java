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

    public long girokontoErstellen(Kunde inhaber) {
        long neueKontennummer = erstelleNeueKontonummer();
        Girokonto girokonto = new Girokonto(inhaber,neueKontennummer,STANDARD_DISPO);
        kontenListe.put(neueKontennummer,girokonto);
        return neueKontennummer;
    }

    public long sparbuchErstellen(Kunde inhaber) {
        long neueKontonummer = erstelleNeueKontonummer();
        Sparbuch sparbuch = new Sparbuch(inhaber,neueKontonummer);
        kontenListe.put(neueKontonummer,sparbuch);
        return neueKontonummer;
    }

    public String getAlleKonten() {
        StringBuilder alleKonten = new StringBuilder();
        for(Konto konto : kontenListe.values()) {
            alleKonten.append(konto).append(System.lineSeparator());
        }
        return String.valueOf(alleKonten);
    }

    public List<Long> getAlleKontonummern() {
        return new ArrayList<>(kontenListe.keySet());
    }

    public boolean geldAbheben(long von, double betrag) {
        if (kontenListe.get(von) != null) {
            try {
                return kontenListe.get(von).abheben(betrag);
            } catch (GesperrtException gesperrtException) {
                return false;
            }
        }
        return false;
        //TODO: Kontonummer nicht vorhanden
    }

    public void geldEinzahlen(long von, double betrag) {
        if (kontenListe.get(von) != null) {
            kontenListe.get(von).einzahlen(betrag);
        }

        //TODO: Kontonummer nicht vorhanden
    }

    public boolean kontoLoeschen(long nummer) {
        if (kontenListe.containsKey(nummer)) {
            kontenListe.remove(nummer);
            vergebeneKontonummern.remove(nummer);
            return true;
        }
        return false;
    }

    public double getKontostand(long nummer) {
        //TODO: Kontonummer nicht vorhanden
        return kontenListe.get(nummer).getKontostand();
    }

    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws GesperrtException{
        //TODO: Kontonummer nicht vorhanden
        Konto vonKonto = kontenListe.get(vonKontonr);
        Konto nachKonto = kontenListe.get(nachKontonr);

        if((vonKonto != null ) && (nachKonto != null)) {
            if((vonKonto instanceof Ueberweisungsfaehig) && (nachKonto instanceof Ueberweisungsfaehig)) {
                ((Ueberweisungsfaehig) vonKonto).ueberweisungAbsenden(betrag,nachKonto.getInhaber().getName(),nachKontonr,this.bankleitzahl,verwendungszweck);
                ((Ueberweisungsfaehig) nachKonto).ueberweisungEmpfangen(betrag,vonKonto.getInhaber().getName(), vonKontonr, this.bankleitzahl, verwendungszweck);
                return true;
            }
            return false;
        }
        return false;
    }

    private long erstelleNeueKontonummer() {
        long neueKontonummer = 1;
        while (vergebeneKontonummern.contains(neueKontonummer)) {
            neueKontonummer++;
        }
        vergebeneKontonummern.add(neueKontonummer);
        return neueKontonummer;
    }
}
