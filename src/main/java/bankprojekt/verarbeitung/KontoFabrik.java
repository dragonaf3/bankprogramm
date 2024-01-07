package bankprojekt.verarbeitung;

/**
 * Dieses Interface definiert eine Fabrik für Konten.
 * Es stellt eine Methode zur Verfügung, um ein Konto zu erstellen.
 */
public interface KontoFabrik {
    /**
     * Erstellt ein neues Konto für einen bestimmten Kunden.
     *
     * @param inhaber     Der Kunde, der das Konto besitzen wird.
     * @param kontonummer Die Kontonummer des zu erstellenden Kontos.
     * @return Das neu erstellte Konto.
     */
    Konto kontoErstellen(Kunde inhaber, long kontonummer);
}