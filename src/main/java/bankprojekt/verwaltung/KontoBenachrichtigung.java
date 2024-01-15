package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Die Klasse KontoBenachrichtigung implementiert das PropertyChangeListener-Interface.
 * Sie wird verwendet, um Änderungen am Kontostand zu überwachen und bei Änderungen eine Benachrichtigung auszugeben.
 */
public class KontoBenachrichtigung implements PropertyChangeListener {
    /**
     * Diese Methode wird aufgerufen, wenn eine Änderung an einer beobachteten Eigenschaft auftritt.
     * In diesem Fall wird eine Nachricht mit der Kontonummer und dem neuen Kontostand ausgegeben.
     *
     * @param evt Das PropertyChangeEvent, das die Änderungsinformationen enthält.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Konto konto = (Konto) evt.getSource();

        System.out.println("Konto:" +
                konto.getKontonummer() +
                System.lineSeparator() +
                "Neuer Kontostand: " +
                konto.getKontostand() +
                System.lineSeparator()
        );
    }
}