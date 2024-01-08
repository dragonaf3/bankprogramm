package bankprojekt.sprachsteuerung;

/**
 * Die Klasse SprachAuswahl ist verantwortlich für die Auswahl der Sprache in der Anwendung.
 * Sie verwendet das Fabrikmuster, um eine Instanz von SprachFabrik zu erstellen, die auf die ausgewählte Sprache abgestimmt ist.
 */
public class SprachAuswahl {
    // Eine statische Variable, die die aktuelle Instanz von SprachFabrik speichert.
    private static SprachFabrik sprachFabrik = new DeutschFabrik();

    /**
     * Diese Methode ermöglicht die Auswahl der Sprache in der Anwendung.
     * Sie nimmt einen Sprachcode als Parameter und erstellt eine entsprechende Instanz von SprachFabrik.
     *
     * @param sprachCode Der Code der ausgewählten Sprache. "DE" für Deutsch, "EN" für Englisch.
     */
    public static void auswahlSprache(String sprachCode) {
        switch (sprachCode) {
            case "DE" -> sprachFabrik = new DeutschFabrik();
            case "EN" -> sprachFabrik = new EnglischFabrik();
            default -> throw new IllegalArgumentException("Sprachcode nicht gültig!");
        }
    }

    /**
     * Diese Methode gibt die aktuelle Instanz von SprachFabrik zurück.
     *
     * @return Die aktuelle Instanz von SprachFabrik.
     */
    public static SprachFabrik getSprachFabrik() {
        return sprachFabrik;
    }
}