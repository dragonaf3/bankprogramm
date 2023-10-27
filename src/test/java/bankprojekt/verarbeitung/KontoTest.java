package bankprojekt.verarbeitung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die Klasse Konto.
 */
class KontoTest {

    Konto giro = new Girokonto(new Kunde("Max", "Mustermann", "Musterstrasse", LocalDate.parse("1976-07-13")), 1234567, 1000);
    Konto spar = new Sparbuch(new Kunde("Max", "Mustermann", "Musterstrasse", LocalDate.parse("1976-07-13")), 1234567);

    @BeforeEach
    void setUp() {
        giro.einzahlen(100);
        spar.einzahlen(100);
    }

    /**
     * Test der Methode abheben mit 0.
     *
     * @throws GesperrtException wenn das Girokonto/Sparbuch gesperrt ist
     */
    @Test
    void abhebenMit0() throws GesperrtException {
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
    void abhebenOptimal() throws GesperrtException {
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
    void abhebenNormal() throws GesperrtException {
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
    void abhebenNormalMitDispo() throws GesperrtException {
        giro.abheben(850, Waehrung.DKK);
        assertEquals(-13.93, giro.getKontostand(), 0.000001);
    }

    /**
     * Test der Methode abheben, wenn das Girokonto in Euro geführt wird und eine andere Währung abgehoben wird und das Konto überzogen werden würde.
     *
     * @throws GesperrtException wenn das Girokonto gesperrt ist
     */
    @Test
    void abhebenDispoUeberzogen() throws GesperrtException {
        assertFalse(giro.abheben(10000, Waehrung.DKK));
    }

    /**
     * Test der Methode abheben, wenn die erlaubte monatliche Abhebesumme für das Sparbuch überstiegen wird.
     *
     * @throws GesperrtException wenn das Sparbuch gesperrt ist
     */
    @Test
    void abhebenSparbuchZuViel() throws GesperrtException {
        spar.einzahlen(500000);
        assertFalse(spar.abheben(20000, Waehrung.DKK));
    }

    /**
     * Test der Exceptions der abheben Methode.
     */
    @Test
    void abhebenExceptions() {

        assertThrows(IllegalArgumentException.class, () -> giro.abheben(-100, Waehrung.DKK));
        assertThrows(IllegalArgumentException.class, () -> spar.abheben(-100, Waehrung.DKK));

        assertThrows(IllegalArgumentException.class, () -> giro.abheben(Double.POSITIVE_INFINITY, Waehrung.DKK));
        assertThrows(IllegalArgumentException.class, () -> spar.abheben(Double.POSITIVE_INFINITY, Waehrung.DKK));

        giro.sperren();
        spar.sperren();
        assertThrows(GesperrtException.class, () -> giro.abheben(100, Waehrung.DKK));
        assertThrows(GesperrtException.class, () -> spar.abheben(100, Waehrung.DKK));
    }
}