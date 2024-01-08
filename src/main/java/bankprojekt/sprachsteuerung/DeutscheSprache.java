package bankprojekt.sprachsteuerung;

import java.io.Serial;

/**
 * Die Klasse DeutscheSprache implementiert die Schnittstelle Sprache.
 * Sie stellt Methoden zur Verfügung, um Beschreibungen für verschiedene Bankdienstleistungen in deutscher Sprache zu erhalten.
 */
public class DeutscheSprache implements Sprache {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String getGirokontoBeschreibung() {
        return "-- GIROKONTO --";
    }

    @Override
    public String getDispoBeschreibung() {
        return "Dispo: ";
    }

    @Override
    public String getSparbuchBeschreibung() {
        return "-- SPARBUCH --";
    }

    @Override
    public String getZinssatzBeschreibung() {
        return "Zinssatz: ";
    }
}
