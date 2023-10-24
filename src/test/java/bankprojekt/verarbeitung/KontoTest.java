package bankprojekt.verarbeitung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KontoTest {

    Konto giro = new Girokonto(new Kunde("Max", "Mustermann", "Musterstrasse", LocalDate.parse("1976-07-13")), 1234567, 1000);
    Konto spar = new Sparbuch(new Kunde("Max", "Mustermann", "Musterstrasse", LocalDate.parse("1976-07-13")), 1234567);

    @BeforeEach
    void setUp() {
        giro.einzahlen(100);
        spar.einzahlen(100);
    }

    @Test
    void abhebenMit0() throws GesperrtException {
        giro.abheben(0, Waehrung.EUR);
        spar.abheben(0, Waehrung.EUR);
        assertEquals(100, giro.getKontostand());
        assertEquals(100, spar.getKontostand());
    }

    @Test
    void abhebenOptimal() throws GesperrtException {
        giro.abheben(50, Waehrung.EUR);
        spar.abheben(50, Waehrung.EUR);
        assertEquals(50, giro.getKontostand());
        assertEquals(50, spar.getKontostand());
    }

    @Test
    void abhebenNormal() throws GesperrtException {
        giro.abheben(50, Waehrung.DKK);
        spar.abheben(50, Waehrung.DKK);
        assertEquals(93.30, giro.getKontostand());
        assertEquals(93.30, spar.getKontostand());
    }

    @Test
    void abhebenNormalMitDispo() throws GesperrtException {
        giro.abheben(850, Waehrung.DKK);
        assertEquals(-13.93, giro.getKontostand(), 0.000001);
    }

    @Test
    void abhebenDispoUeberzogen() throws GesperrtException {
        assertFalse(giro.abheben(10000, Waehrung.DKK));
    }

    @Test
    void abhebenSparbuchZuViel() throws GesperrtException {
        spar.einzahlen(500000);
        assertFalse(spar.abheben(20000, Waehrung.DKK));
    }

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