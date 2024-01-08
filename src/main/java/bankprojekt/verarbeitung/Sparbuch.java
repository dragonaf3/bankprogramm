package bankprojekt.verarbeitung;

import bankprojekt.sprachsteuerung.SprachAuswahl;
import bankprojekt.sprachsteuerung.Sprache;

import java.io.Serial;
import java.time.LocalDate;

/**
 * ein Sparbuch, d.h. ein Konto, das nur recht eingeschränkt genutzt
 * werden kann. Insbesondere darf man monatlich nur höchstens 2000€
 * abheben, wobei der Kontostand nie unter 0,50€ fallen darf.
 *
 * @author Doro
 */
public class Sparbuch extends Konto {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
     */
    private double zinssatz;

    /**
     * Monatlich erlaubter Gesamtbetrag für Abhebungen (in Euro)
     */
    public static final double ABHEBESUMME = 2000;

    /**
     * Betrag, der im aktuellen Monat bereits abgehoben wurde
     */
    private double bereitsAbgehoben = 0;

    /**
     * Monat und Jahr der letzten Abhebung
     */
    private LocalDate zeitpunkt = LocalDate.now();
    private Sprache sprache = SprachAuswahl.getSprachFabrik().erstelleSprache();

    /**
     * ein Standard-Sparbuch
     */
    public Sparbuch() {
        zinssatz = 0.03;
    }

    /**
     * ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
     *
     * @param inhaber     der Kontoinhaber
     * @param kontonummer die Wunsch-Kontonummer
     * @throws IllegalArgumentException wenn inhaber null ist
     */
    public Sparbuch(Kunde inhaber, long kontonummer) {
        super(inhaber, kontonummer);
        zinssatz = 0.03;
    }

    @Override
    public String toString() {
        String ausgabe = sprache.getSparbuchBeschreibung() + System.lineSeparator() +
                super.toString()
                + sprache.getZinssatzBeschreibung() + this.zinssatz * 100 + "%" + System.lineSeparator();
        return ausgabe;
    }

    @Override
    public void waehrungswechsel(Waehrung neu) {
        this.bereitsAbgehoben = Waehrung.waehrungswechsel(this.bereitsAbgehoben, this.getWaehrung(), neu);
        super.waehrungswechsel(neu);
    }

    //Implementierung zur Template-Methode

    /**
     * Überprüft, ob eine Abhebung erlaubt ist oder nicht.
     *
     * @param betrag Der Betrag, der abgehoben werden soll.
     * @return true, wenn die Abhebung erlaubt ist, false andernfalls.
     * <p>
     * Diese Methode überprüft zunächst, ob der aktuelle Monat und das aktuelle Jahr sich von dem Monat und Jahr der letzten Abhebung unterscheiden.
     * Wenn sie sich unterscheiden, wird der in diesem Monat bereits abgehobene Betrag auf 0 zurückgesetzt.
     * <p>
     * Dann überprüft sie zwei Bedingungen:
     * 1. Ob der Kontostand nach der Abhebung größer oder gleich 0,50 wäre.
     * 2. Ob der in diesem Monat einschließlich der neuen Abhebung abgehobene Gesamtbetrag kleiner oder gleich dem maximalen Abhebungslimit wäre.
     * <p>
     * Wenn beide Bedingungen erfüllt sind, gibt die Methode true zurück und erlaubt die Abhebung.
     * Wenn eine der Bedingungen nicht erfüllt ist, gibt die Methode false zurück und verbietet die Abhebung.
     */
    @Override
    protected boolean darfAbheben(double betrag) {
        LocalDate heute = LocalDate.now();
        if (heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear()) {
            this.bereitsAbgehoben = 0;
        }
        return getKontostand() - betrag >= 0.50 &&
                bereitsAbgehoben + betrag <= getWaehrung().euroInWaehrungUmrechnen(Sparbuch.ABHEBESUMME);
    }

    /**
     * Führt die Handlungen nach einer Abhebung durch.
     *
     * @param betrag Der Betrag, der abgehoben wurde.
     *               <p>
     *               Diese Methode erhöht den bereits abgehobenen Betrag um den abgehobenen Betrag und setzt den Zeitpunkt der letzten Abhebung auf das aktuelle Datum.
     */
    @Override
    protected void handlungNachAbhebung(double betrag) {
        bereitsAbgehoben += betrag;
        this.zeitpunkt = LocalDate.now();
    }
}
