package bankprojekt.verarbeitung;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaehrungTest {

    @Test
    void waehrungswechselEuroNachEuro() {
        double betrag = 100;
        double erwartet = 100;
        double ergebnis = Waehrung.waehrungswechsel(betrag, Waehrung.EUR, Waehrung.EUR);
        assertEquals(erwartet, ergebnis);
    }

    @Test
    void waehrungswechselEuroNachNichtEuro() {
        double betrag = 100;
        double erwartet = 195.58;
        double ergebnis = Waehrung.waehrungswechsel(betrag, Waehrung.EUR, Waehrung.BGN);
        assertEquals(erwartet, ergebnis);
    }

    @Test
    void waehrungswechselNichtEuroNachEuro() {
        double betrag = 100;
        double erwartet = 51.13;
        double ergebnis = Waehrung.waehrungswechsel(betrag, Waehrung.BGN, Waehrung.EUR);
        assertEquals(erwartet, ergebnis);
    }

    @Test
    void waehrungswechselNichtEuroNachNichtEuro() {
        double betrag = 100;
        double erwartet = 381.45;
        double ergebnis = Waehrung.waehrungswechsel(betrag, Waehrung.BGN, Waehrung.DKK);
        assertEquals(erwartet, ergebnis);
    }

    @Test
    void waehrungswechselMit0() {
        double betrag = 0;
        double erwartet = 0;
        double ergebnis = Waehrung.waehrungswechsel(betrag, Waehrung.BGN, Waehrung.DKK);
        assertEquals(erwartet, ergebnis);
    }

    @Test
    void waehrungswechselMitNegativenZahlen() {
        double betrag = -100;
        double erwartet = -381.45;
        double ergebnis = Waehrung.waehrungswechsel(betrag, Waehrung.BGN, Waehrung.DKK);
        assertEquals(erwartet, ergebnis);
    }


}