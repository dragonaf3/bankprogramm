package bankprojekt.verwaltung;

/**
 * Tritt bei einem Aufruf mit einer ungültigen Kontonummer auf.
 */
public class UngueltigeKontonummerException extends Exception {
    /**
     * Zugriff auf das Konto mit der angegebenen Kontonummer.
     *
     * @param kontonummer Die Nummer des Kontos, welche ungültig ist.
     */
    public UngueltigeKontonummerException(long kontonummer) {
        super("Folgende Kontonummer ist ungültig: " + kontonummer);
    }
}
