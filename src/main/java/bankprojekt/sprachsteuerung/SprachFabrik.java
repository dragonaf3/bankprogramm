package bankprojekt.sprachsteuerung;

/**
 * Das SprachFabrik Interface definiert die Methode erstelleSprache.
 * Diese Methode wird verwendet, um ein Sprache-Objekt zu erstellen.
 */
public interface SprachFabrik {
    /**
     * Die Methode erstelleSprache wird verwendet, um ein Sprache-Objekt zu erstellen.
     *
     * @return Gibt ein neues Sprache-Objekt zurück.
     */
    Sprache erstelleSprache();
}