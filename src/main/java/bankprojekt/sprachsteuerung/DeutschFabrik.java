package bankprojekt.sprachsteuerung;

/**
 * Die Klasse DeutschFabrik implementiert das Interface SprachFabrik.
 * Sie ist verantwortlich für die Erstellung von Instanzen der Klasse DeutscheSprache.
 */
public class DeutschFabrik implements SprachFabrik {

    /**
     * Diese Methode erstellt eine neue Instanz der Klasse DeutscheSprache und gibt sie zurück.
     *
     * @return eine neue Instanz der Klasse DeutscheSprache
     */
    @Override
    public Sprache erstelleSprache() {
        return new DeutscheSprache();
    }
}