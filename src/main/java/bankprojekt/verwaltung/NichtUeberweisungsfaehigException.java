package bankprojekt.verwaltung;

/**
 * Tritt auf, wenn ein Konto nicht überweisungsfähig ist.
 */
public class NichtUeberweisungsfaehigException extends Exception {
    /**
     * Erzeugt eine neue Exception mit der übergebenen Fehlermeldung.
     *
     * @param kontonummer Kontonummer des nicht überweisungsfähigen Kontos
     */
    public NichtUeberweisungsfaehigException(long kontonummer) {
        super("Konto " + kontonummer + " ist nicht überweisungsfähig!");
    }
}
