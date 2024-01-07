package bankprojekt.verarbeitung;

/**
 * Eine Fabrikklasse zur Erstellung von Sparbuch-Objekten.
 */
public class SparbuchFabrik implements KontoFabrik {

    /**
     * Erstellt ein neues Sparbuch-Konto.
     *
     * @param inhaber     Der Inhaber des Kontos.
     * @param kontonummer Die Kontonummer des Kontos.
     * @return Ein neues Sparbuch-Objekt.
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        return new Sparbuch(inhaber, kontonummer);
    }
}