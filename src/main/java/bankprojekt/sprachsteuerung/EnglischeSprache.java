package bankprojekt.sprachsteuerung;

/**
 * Die Klasse EnglischeSprache implementiert die Schnittstelle Sprache.
 * Sie stellt Methoden zur Verfügung, um Beschreibungen für verschiedene Bankdienstleistungen in englischer Sprache zu erhalten.
 */
public class EnglischeSprache implements Sprache {
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
