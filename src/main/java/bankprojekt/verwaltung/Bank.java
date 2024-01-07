package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Diese Klasse repräsentiert eine Bank mit Kontenverwaltung
 */
public class Bank implements Cloneable, Serializable {
    /**
     * Standard-Dispo für ein Girokonto, welche von der Bank vorgegeben ist
     */
    public static final double STANDARD_DISPO = 1000;
    private final long bankleitzahl;
    private final Map<Long, Konto> kontenListe;
    @Serial
    private static final long serialVersionUID = 1L;


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

    /**
     * Erstellt ein neues Konto für einen gegebenen Kunden und fügt es zur Kontenliste der Bank hinzu.
     *
     * @param kontoFabrik Die Fabrik, die zur Erstellung des Kontos verwendet wird.
     * @param inhaber     Der Kunde, für den das Konto erstellt wird.
     * @return Die Kontonummer des neu erstellten Kontos.
     * @throws IllegalArgumentException wenn der Inhaber null ist.
     */
    public long kontoErstellen(KontoFabrik kontoFabrik, Kunde inhaber) throws IllegalArgumentException {
        if (inhaber == null) {
            throw new IllegalArgumentException("Inhaber darf nicht null sein");
        }
        long neueKontonummer = erstelleNeueKontonummer();
        Konto neuesKonto = kontoFabrik.kontoErstellen(inhaber, neueKontonummer);
        kontenListe.put(neueKontonummer, neuesKonto);
        return neueKontonummer;
    }

    /**
     * Erstellt einen String von allen Kontonummern und den zugehörigen Kontoständen.
     *
     * @return alle Kontonummern und Kontostände der Bank als String
     */
    public String getAlleKonten() {
        return kontenListe.values().stream()
                .map(konto -> konto.getKontonummer() + " " + konto.getKontostand())
                .collect(Collectors.joining(System.lineSeparator()));
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

    /**
     * Diese Methode sperrt alle Konten, deren Kontostand im Minus ist.
     */
    public void pleitegeierSperren() {
        kontenListe.values().stream()
                .filter(konto -> konto.getKontostand() < 0)
                .forEach(Konto::sperren);
    }

    /**
     * Diese Methode liefert eine Liste aller Kunden,
     * die auf einem Konto einen Kontostand haben, der mindestens minimum beträgt.
     *
     * @param minimum der minimale Kontostand
     * @return Liste aller Kunden, die auf einem Konto einen Kontostand haben, der mindestens minimum beträgt
     */
    public List<Kunde> getKundenMitVollemKonto(double minimum) {
        return kontenListe.values().stream()
                .filter(konto -> konto.getKontostand() >= minimum)
                .map(Konto::getInhaber)
                .distinct() //Hat vielleicht mehrere Konten
                .collect(Collectors.toList());
    }

    /**
     * Diese Methode liefert die Namen und Adressen aller Kunden der Bank. Doppelte Kunden sollen
     * dabei aussortiert werden. Sortieren Sie die Liste nach dem Vornamen.
     *
     * @return Namen und Adressen aller Kunden der Bank
     */
    public String getKundenadressen() {
        return kontenListe.values().stream()
                .map(Konto::getInhaber)
                .distinct()
                .sorted(Comparator.comparing(Kunde::getVorname))
                .map(kunde -> kunde.getName() + ", " + kunde.getAdresse())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Diese Methode liefert eine Liste aller freien Kontonummern, die im von Ihnen vergebenen Bereich
     * liegen.
     *
     * @return Liste aller freien Kontonummern, die im vergebenen Bereich liegen
     */
    public List<Long> getKontonummernLuecken() {
        long maxKontonummer = Collections.max(kontenListe.keySet());
        return LongStream.range(1, maxKontonummer)
                .filter(kontonummer -> !kontenListe.containsKey(kontonummer))
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Diese Methode liefert eine Liste aller Kunden, deren Gesamteinlage auf all ihren
     * Konten mehr als minimum beträgt.
     *
     * @param minimum die minimale Gesamteinlage
     * @return Liste aller Kunden, deren Gesamteinlage auf all ihren Konten mehr als minimum beträgt
     */
    public List<Kunde> getAlleReichenKunden(double minimum) {
        return kontenListe.values().stream()
                .collect(Collectors.groupingBy(Konto::getInhaber, Collectors.summingDouble(Konto::getKontostand)))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > minimum)
                .map(Map.Entry::getKey)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Klont das aktuelle Bankobjekt.
     * Diese Methode verwendet Serialisierung, um eine tiefe Kopie des Bankobjektes zu ermöglichen.
     * <p>
     * Das Bankobjekt wird zunächst in ein Byte-Array serialisiert und anschließend in ein neues Bankobjekt deserialisiert.
     *
     * @return eine tiefe Kopie des aktuellen Bankobjektes
     */
    @Override
    public Bank clone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(this);
            oos.flush();
            byte[] bytes = bos.toByteArray();

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);

            return (Bank) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Fehler beim kopieren der Bank!", e);
        }
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
