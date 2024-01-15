package bankprojekt.verarbeitung.fabrik;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.fabrik.KontoFabrik;

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
     * @throws IllegalArgumentException wenn der Inhaber null ist.
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) throws IllegalArgumentException {
        return new Girokonto(inhaber, kontonummer, STANDARD_DISPO);
    }
}