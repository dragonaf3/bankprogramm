package bankprojekt.sprachsteuerung;

/**
 * Die Klasse EnglischFabrik implementiert das Interface SprachFabrik.
 * Sie ist verantwortlich für die Erstellung von Instanzen der EnglischeSprache-Klasse.
 */
public class EnglischFabrik implements SprachFabrik{

    /**
     * Diese Methode erstellt eine neue Instanz der Klasse EnglischeSprache.
     * Sie überschreibt die Methode erstelleSprache() des SprachFabrik-Interfaces.
     *
     * @return eine neue Instanz der Klasse EnglischeSprache
     */
    @Override
    public Sprache erstelleSprache() {
        return new EnglischeSprache();
    }
}