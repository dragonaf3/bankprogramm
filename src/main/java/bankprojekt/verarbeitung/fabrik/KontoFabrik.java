package bankprojekt.verarbeitung.fabrik;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

/**
 * Abstrakte Klasse KontoFabrik zur Erstellung von Konten.
 * Diese Klasse dient als Vorlage für spezifische KontoFabriken.
 */
public abstract class KontoFabrik {
    /**
     * Abstrakte Methode zur Erstellung eines Kontos.
     * Diese Methode muss in jeder konkreten Unterklasse implementiert werden.
     *
     * @param inhaber     Der Kunde, der das Konto besitzen wird.
     * @param kontonummer Die Kontonummer des zu erstellenden Kontos.
     * @return Das erstellte Konto.
     * @throws IllegalArgumentException wenn der Inhaber null ist.
     */
    public abstract Konto kontoErstellen(Kunde inhaber, long kontonummer) throws IllegalArgumentException;
}