package bankprojekt.sprachsteuerung;

import java.io.Serializable;

/**
 * Das Interface Sprache definiert Methoden zur Bereitstellung sprachspezifischer Beschreibungen
 * für verschiedene Elemente des Bankprojekts. Derzeit nur Girokonto und Sparbuch.
 */
public interface Sprache extends Serializable {

    //Girokonto

    /**
     * Gibt eine sprachspezifische Beschreibung für ein Girokonto zurück.
     *
     * @return Eine String-Beschreibung eines Girokontos in der gewählten Sprache.
     */
    String getGirokontoBeschreibung();

    /**
     * Gibt eine sprachspezifische Beschreibung für den Dispo eines Girokontos zurück.
     *
     * @return Eine String-Beschreibung des Dispos eines Girokontos in der gewählten Sprache.
     */
    String getDispoBeschreibung();

    //Sparbuch

    /**
     * Gibt eine sprachspezifische Beschreibung für ein Sparbuch zurück.
     *
     * @return Eine String-Beschreibung eines Sparbuchs in der gewählten Sprache.
     */
    String getSparbuchBeschreibung();

    /**
     * Gibt eine sprachspezifische Beschreibung für den Zinssatz eines Sparbuchs zurück.
     *
     * @return Eine String-Beschreibung des Zinssatzes eines Sparbuchs in der gewählten Sprache.
     */
    String getZinssatzBeschreibung();
}