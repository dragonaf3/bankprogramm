package bankprojekt.verarbeitung;

import static bankprojekt.verwaltung.Bank.STANDARD_DISPO;

/**
 * Eine Fabrikklasse zur Erstellung von Girokonten-Objekten.
 */
public class GirokontoFabrik extends KontoFabrik {

    /**
     * Erstellt ein neues Girokonto mit dem Standard Dispo.
     *
     * @param inhaber     Der Inhaber des Kontos.
     * @param kontonummer Die Kontonummer des Kontos.
     * @return Ein neues Girokonto mit dem gegebenen Inhaber und der Kontonummer.
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        return new Girokonto(inhaber, kontonummer, STANDARD_DISPO);
    }
}