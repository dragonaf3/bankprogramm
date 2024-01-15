package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.KontoBenachrichtigung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * Testklasse für die Klasse Konto.
 */
class KontoTest {
    private Konto giro = new Girokonto(new Kunde("Max", "Mustermann", "Musterstrasse", LocalDate.parse("1976-07-13")), 1234567, 1000);
    private Konto spar = new Sparbuch(new Kunde("Max", "Mustermann", "Musterstrasse", LocalDate.parse("1976-07-13")), 1234567);
    @Mock
    private PropertyChangeListener listenerMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giro.einzahlen(100);
        spar.einzahlen(100);
    }

    /**
     * Test der Methode abheben mit 0.
     *
     * @throws GesperrtException wenn das Girokonto/Sparbuch gesperrt ist
     */
    @Test
    void testAbhebenMit0() throws GesperrtException {
        giro.abheben(0, Waehrung.EUR);
        spar.abheben(0, Waehrung.EUR);
        assertEquals(100, giro.getKontostand());
        assertEquals(100, spar.getKontostand());
    }

    /**
     * Test der Methode abheben, wenn das Girokonto/Sparbuch in Euro geführt wird und Euro abgehoben wird.
     *
     * @throws GesperrtException wenn das Girokonto/Sparbuch gesperrt ist
     */
    @Test
    void testAbhebenOptimal() throws GesperrtException {
        giro.abheben(50, Waehrung.EUR);
        spar.abheben(50, Waehrung.EUR);
        assertEquals(50, giro.getKontostand());
        assertEquals(50, spar.getKontostand());
    }

    /**
     * Test der Methode abheben, wenn das Girokonto/Sparbuch in Euro geführt wird und eine andere Währung abgehoben wird.
     *
     * @throws GesperrtException wenn das Girokonto/Sparbuch gesperrt ist
     */
    @Test
    void testAbhebenNormal() throws GesperrtException {
        giro.abheben(50, Waehrung.DKK);
        spar.abheben(50, Waehrung.DKK);
        assertEquals(93.30, giro.getKontostand());
        assertEquals(93.30, spar.getKontostand());
    }

    /**
     * Test der Methode abheben, wenn das Girokonto in Euro geführt wird, eine andere Währung abgehoben wird und das Konto in den Dispo kommt.
     *
     * @throws GesperrtException wenn das Girokonto gesperrt ist
     */
    @Test
    void testAbhebenNormalMitDispo() throws GesperrtException {
        giro.abheben(850, Waehrung.DKK);
        assertEquals(-13.93, giro.getKontostand(), 0.000001);
    }

    /**
     * Test der Methode abheben, wenn das Girokonto in Euro geführt wird und eine andere Währung abgehoben wird und das Konto überzogen werden würde.
     *
     * @throws GesperrtException wenn das Girokonto gesperrt ist
     */
    @Test
    void testAbhebenDispoUeberzogen() throws GesperrtException {
        assertFalse(giro.abheben(10000, Waehrung.DKK));
    }

    /**
     * Test der Methode abheben, wenn die erlaubte monatliche Abhebesumme für das Sparbuch überstiegen wird.
     *
     * @throws GesperrtException wenn das Sparbuch gesperrt ist
     */
    @Test
    void testAbhebenSparbuchZuViel() throws GesperrtException {
        spar.einzahlen(500000);
        assertFalse(spar.abheben(20000, Waehrung.DKK));
    }

    /**
     * Test der Exceptions der abheben Methode.
     */
    @Test
    void testAbhebenExceptions() {

        assertThrows(IllegalArgumentException.class, () -> giro.abheben(-100, Waehrung.DKK));
        assertThrows(IllegalArgumentException.class, () -> spar.abheben(-100, Waehrung.DKK));

        assertThrows(IllegalArgumentException.class, () -> giro.abheben(Double.POSITIVE_INFINITY, Waehrung.DKK));
        assertThrows(IllegalArgumentException.class, () -> spar.abheben(Double.POSITIVE_INFINITY, Waehrung.DKK));

        giro.sperren();
        spar.sperren();
        assertThrows(GesperrtException.class, () -> giro.abheben(100, Waehrung.DKK));
        assertThrows(GesperrtException.class, () -> spar.abheben(100, Waehrung.DKK));
    }

    /**
     *
     */
    /**
     * Dieser Test überprüft die Methode anmelden und die Benachrichtigungsfunktion.
     * Zuerst wird ein Listener am Girokonto angemeldet.
     * Dann wird der aktuelle Kontostand gespeichert und der neue Kontostand berechnet, nachdem 100 eingezahlt wurden.
     * Nach der Einzahlung wird überprüft, ob der Listener eine PropertyChangeEvent erhalten hat.
     * Es wird erwartet, dass der Listener eine Benachrichtigung erhält.
     */
    @Test
    void testAnmeldenUndBenachrichtigung() {
        giro.anmelden(listenerMock);
        double alterKontostand = giro.getKontostand();
        double neuerKontostand = alterKontostand + 100;

        giro.einzahlen(100);

        verify(listenerMock).propertyChange(argThat(event ->
                "Kontostand Änderung".equals(event.getPropertyName()) &&
                        Objects.equals(alterKontostand, event.getOldValue()) &&
                        Objects.equals(neuerKontostand, event.getNewValue()) &&
                        event.getSource().equals(giro)
        ));
    }

    /**
     * Dieser Test überprüft die Methode abmelden.
     * Zuerst wird ein Listener am Girokonto angemeldet und dann wieder abgemeldet.
     * Nachdem 100 eingezahlt wurden, wird überprüft, ob der Listener eine PropertyChangeEvent erhalten hat.
     * Es wird erwartet, dass der Listener keine Benachrichtigung erhält, da er vor der Einzahlung abgemeldet wurde.
     */
    @Test
    void testAbmelden() {
        giro.anmelden(listenerMock);
        giro.abmelden(listenerMock);
        giro.einzahlen(100);
        verify(listenerMock, never()).propertyChange(argThat(event ->
                "Kontostand Änderung".equals(event.getPropertyName()) &&
                        Objects.equals(100.0, event.getOldValue()) &&
                        Objects.equals(200.0, event.getNewValue()) &&
                        event.getSource().equals(giro)
        ));
    }
}