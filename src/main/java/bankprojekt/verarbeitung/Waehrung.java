package bankprojekt.verarbeitung;

import static org.decimal4j.util.DoubleRounder.round;

/**
 * Stellt die von der Bank angebotenen Währungen dar.
 */
public enum Waehrung {

    /**
     * Euro
     */
    EUR(1.0),

    /**
     * Bulgarische Lew
     */
    BGN(1.9558),

    /**
     * Dänische Krone
     */
    DKK(7.4604),

    /**
     * Mazedonischer Denar
     */
    MKD(61.62);

    private final double umrechnungskurs;

    Waehrung(double umrechnungskurs) {
        this.umrechnungskurs = umrechnungskurs;
    }

    /**
     * Rechnet den Euro-Betrag in die jeweilige Währung um.
     *
     * @param betrag Euro-Betrag
     * @return Der umgerechnete Betrag in der neuen Währung
     */
    public double euroInWaehrungUmrechnen(double betrag) {
        return round(betrag * umrechnungskurs, 2);
    }

    /**
     * Rechnet den Betrag in der jeweiligen Währung in Euro um.
     *
     * @param betrag Betrag in der jeweiligen Währung
     * @return Der umgerechnete Betrag in Euro
     */
    public double waehrungInEuroUmrechnen(double betrag) {
        return round(betrag / umrechnungskurs, 2);
    }

    /**
     * Rechnet den Betrag in der angegebenen Waehrung in die Zielwaehrung um.
     *
     * @param betrag          Betrag in der angegebenen Waehrung
     * @param anfangsWaehrung Waehrung, in der der Betrag angegeben ist
     * @param zielWaehrung    Waehrung, in die der Betrag umgerechnet werden soll
     */
    public static double waehrungswechsel(double betrag, Waehrung anfangsWaehrung, Waehrung zielWaehrung) {
        double betragInEuro = anfangsWaehrung.waehrungInEuroUmrechnen(betrag);
        return round(zielWaehrung.euroInWaehrungUmrechnen(betragInEuro), 2);
    }
}

