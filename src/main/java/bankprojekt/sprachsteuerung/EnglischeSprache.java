package bankprojekt.sprachsteuerung;

import java.io.Serial;

/**
 * Die Klasse EnglischeSprache implementiert die Schnittstelle Sprache.
 * Sie stellt Methoden zur Verfügung, um Beschreibungen für verschiedene Bankdienstleistungen in englischer Sprache zu erhalten.
 */
public class EnglischeSprache implements Sprache {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String getGirokontoBeschreibung() {
        return "-- CURRENT ACCOUNT --";
    }

    @Override
    public String getDispoBeschreibung() {
        return "Overdraft: ";
    }

    @Override
    public String getSparbuchBeschreibung() {
        return "-- SAVINGS BOOK --";
    }

    @Override
    public String getZinssatzBeschreibung() {
        return "Interest rate: ";
    }
}
