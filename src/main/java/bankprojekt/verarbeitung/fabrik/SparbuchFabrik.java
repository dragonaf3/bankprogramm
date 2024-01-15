package bankprojekt.verarbeitung.fabrik;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verarbeitung.fabrik.KontoFabrik;

/**
 * Eine Fabrikklasse zur Erstellung von Sparbuch-Objekten.
 */
public class SparbuchFabrik extends KontoFabrik {

    /**
     * Erstellt ein neues Sparbuch-Konto.
     *
     * @param inhaber     Der Inhaber des Kontos.
     * @param kontonummer Die Kontonummer des Kontos.
     * @return Ein neues Sparbuch-Objekt.
     * @throws IllegalArgumentException wenn der Inhaber null ist.
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) throws IllegalArgumentException {
        return new Sparbuch(inhaber, kontonummer);
    }
}