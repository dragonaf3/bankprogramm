package bankprojekt.verarbeitung;

import com.google.common.primitives.Doubles;

/**
 * stellt ein allgemeines Bank-Konto dar
 */
public abstract class Konto implements Comparable<Konto> {
    /**
     * der Kontoinhaber
     */
    private Kunde inhaber;

    /**
     * die Kontonummer
     */
    private final long nummer;

    /**
     * der aktuelle Kontostand
     */
    private double kontostand;

    /**
     * die Währung des Kontos
     */
    private Waehrung waehrung;

    /**
     * setzt den aktuellen Kontostand
     *
     * @param kontostand neuer Kontostand
     */
    protected void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    /**
     * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
     * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
     */
    private boolean gesperrt;

    /**
     * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
     * der anfängliche Kontostand wird auf 0 gesetzt. Die Standard Waehrung ist Euro.
     *
     * @param inhaber     der Inhaber
     * @param kontonummer die gewünschte Kontonummer
     * @throws IllegalArgumentException wenn der inhaber null ist
     */
    public Konto(Kunde inhaber, long kontonummer) {
        if (inhaber == null) throw new IllegalArgumentException("Inhaber darf nicht null sein!");
        this.inhaber = inhaber;
        this.nummer = kontonummer;
        this.kontostand = 0;
        this.gesperrt = false;
        this.waehrung = Waehrung.EUR;
    }

    /**
     * setzt alle Eigenschaften des Kontos auf Standardwerte
     */
    public Konto() {
        this(Kunde.MUSTERMANN, 1234567);
    }

    /**
     * liefert den Kontoinhaber zurück
     *
     * @return der Inhaber
     */
    public final Kunde getInhaber() {
        return this.inhaber;
    }

    /**
     * setzt den Kontoinhaber
     *
     * @param kinh neuer Kontoinhaber
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn kinh null ist
     */
    public final void setInhaber(Kunde kinh) throws GesperrtException {
        if (kinh == null) throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
        if (this.gesperrt) throw new GesperrtException(this.nummer);
        this.inhaber = kinh;

    }

    /**
     * liefert den aktuellen Kontostand
     *
     * @return Kontostand
     */
    public final double getKontostand() {
        return kontostand;
    }

    /**
     * liefert die Kontonummer zurück
     *
     * @return Kontonummer
     */
    public final long getKontonummer() {
        return nummer;
    }

    /**
     * liefert die Währung des Kontos zurück
     *
     * @return Waehrung
     */
    public final Waehrung getWaehrung() {
        return waehrung;
    }

    /**
     * setzt die Währung des Kontos
     *
     * @param waehrung Waehrung
     */
    private void setWaehrung(Waehrung waehrung) {
        this.waehrung = waehrung;
    }

    /**
     * liefert zurück, ob das Konto gesperrt ist oder nicht
     *
     * @return true, wenn das Konto gesperrt ist
     */
    public final boolean isGesperrt() {
        return gesperrt;
    }

    /**
     * Erhöht den Kontostand um den eingezahlten Betrag.
     *
     * @param betrag double
     * @throws IllegalArgumentException wenn der Betrag negativ ist
     */
    public void einzahlen(double betrag) throws IllegalArgumentException {
        if (betrag < 0 || !Doubles.isFinite(betrag)) {
            throw new IllegalArgumentException("Falscher Betrag");
        }
        setKontostand(getKontostand() + betrag);
    }

    /**
     * Erhöht den Kontostand um den eingezahlten Betrag der jeweiligen Waehrung.
     *
     * @param betrag double
     * @param w      Waehrung
     * @throws IllegalArgumentException wenn der Betrag negativ ist
     */
    public void einzahlen(double betrag, Waehrung w) throws IllegalArgumentException {
        einzahlen(Waehrung.waehrungswechsel(betrag, w, this.getWaehrung()));
    }

    @Override
    public String toString() {
        String ausgabe;
        ausgabe = "Kontonummer: " + this.getKontonummerFormatiert() + System.getProperty("line.separator");
        ausgabe += "Inhaber: " + this.inhaber;
        ausgabe += "Aktueller Kontostand: " + getKontostandFormatiert() + " ";
        ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
        return ausgabe;
    }

    /**
     * Mit dieser Methode wird der geforderte Betrag, in der vom Konto geführten Waehrung, vom Konto abgehoben,
     * wenn es nicht gesperrt ist und die speziellen Abheberegeln des jeweiligen Kontotyps die Abhebung erlauben.
     *
     * @param betrag double
     * @return true, wenn die Abhebung geklappt hat,
     * false, wenn sie abgelehnt wurde
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der betrag negativ oder unendlich oder NaN ist
     */
    public abstract boolean abheben(double betrag) throws GesperrtException, IllegalArgumentException;

    /**
     * Mit dieser Methode wird der geforderte Betrag vom Konto in der gewünschten Waehrung abgehoben,
     * wenn es nicht gesperrt ist und die speziellen Abheberegeln des jeweiligen Kontotyps die Abhebung erlauben.
     *
     * @param betrag double
     * @param w      Waehrung
     * @return true, wenn die Abhebung geklappt hat,
     * false, wenn sie abgelehnt wurde
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der betrag negativ oder unendlich oder NaN ist
     */
    public boolean abheben(double betrag, Waehrung w) throws GesperrtException, IllegalArgumentException {
        return abheben(Waehrung.waehrungswechsel(betrag, w, this.getWaehrung()));
    }

    /**
     * Mit dieser Methode wird die Waehrung des aktuellen Kontos gewechselt.
     *
     * @param neu
     */
    public void waehrungswechsel(Waehrung neu) {
        this.setKontostand(Waehrung.waehrungswechsel(this.kontostand, this.getWaehrung(), neu));
        this.setWaehrung(neu);
    }

    /**
     * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
     */
    public final void sperren() {
        this.gesperrt = true;
    }

    /**
     * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
     */
    public final void entsperren() {
        this.gesperrt = false;
    }


    /**
     * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
     *
     * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
     */
    public final String getGesperrtText() {
        if (this.gesperrt) {
            return "GESPERRT";
        } else {
            return "";
        }
    }

    /**
     * liefert die ordentlich formatierte Kontonummer
     *
     * @return auf 10 Stellen formatierte Kontonummer
     */
    public String getKontonummerFormatiert() {
        return String.format("%10d", this.nummer);
    }

    /**
     * liefert den ordentlich formatierten Kontostand mit entsprechender Waehrung.
     *
     * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol
     */
    public String getKontostandFormatiert() {
        return String.format("%10.2f %s", this.kontostand, this.getWaehrung());
    }

    /**
     * Vergleich von this mit other; Zwei Konten gelten als gleich,
     * wen sie die gleiche Kontonummer haben
     *
     * @param other das Vergleichskonto
     * @return true, wenn beide Konten die gleiche Nummer haben
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;
        if (this.nummer == ((Konto) other).nummer) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
    }

    @Override
    public int compareTo(Konto other) {
        if (other.getKontonummer() > this.getKontonummer()) return -1;
        if (other.getKontonummer() < this.getKontonummer()) return 1;
        return 0;
    }
}
