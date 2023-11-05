package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;
    private Kunde kunde1;
    private Kunde kunde2;

    //Setup

    /**
     * Setup, erstellt eine Bank und zwei Kund*Innen
     */
    @BeforeEach
    void setUp() {
        bank = new Bank(12345); // Bankleitzahl als Beispielwert
        kunde1 = new Kunde("Max", "Mustermann", "Musterstrasse 3", LocalDate.parse("1976-07-13"));
        kunde2 = new Kunde("Erika", "Musterfrau", "Straßenmuster 5", LocalDate.parse("1996-07-13"));
    }

    //__________________________________________________________________________________________________________________

    //Konstruktor und getter Test

    /**
     * Testet den Konstruktor und die getter Methode
     */
    @Test
    void bankErstellenTest() {
        assertEquals(12345, bank.getBankleitzahl());
    }

    //__________________________________________________________________________________________________________________

    //Girokonto Erstellungen Tests
    @Test
    void girokontoErstellen_normal_Test() {
        long kontonummer = bank.girokontoErstellen(kunde1);
        long expected = 1;

        long kontonummer2 = bank.girokontoErstellen(kunde2);
        long expected2 = 2;

        assertEquals(expected, kontonummer);
        assertEquals(expected2, kontonummer2);
    }

    @Test
    void girokontoErstellen_mitLoeschen_Test() {
        long kontonummer = bank.girokontoErstellen(kunde1);
        long expected = 1;

        long kontonummer2 = bank.girokontoErstellen(kunde2);
        long expected2 = 2;

        assertEquals(expected, kontonummer);
        assertEquals(expected2, kontonummer2);

        bank.kontoLoeschen(kontonummer);
        long kontonummer3 = bank.girokontoErstellen(kunde1);
        long expected3 = 1;

        assertEquals(expected3, kontonummer3);

        long kontonummer4 = bank.girokontoErstellen(new Kunde());
        long expected4 = 3;

        assertEquals(expected4, kontonummer4);
    }

    //__________________________________________________________________________________________________________________

    //Sparbuch Erstellungen Tests
    @Test
    void sparbuchErstellen_normal_Test() {
        long kontonummer = bank.sparbuchErstellen(kunde1);
        long expected = 1;

        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        long expected2 = 2;

        assertEquals(expected, kontonummer);
        assertEquals(expected2, kontonummer2);
    }

    @Test
    void sparbuchErstellen_mitLoeschen_Test() {
        long kontonummer = bank.sparbuchErstellen(kunde1);
        long expected = 1;

        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        long expected2 = 2;

        assertEquals(expected, kontonummer);
        assertEquals(expected2, kontonummer2);

        bank.kontoLoeschen(kontonummer);
        long kontonummer3 = bank.sparbuchErstellen(kunde1);
        long expected3 = 1;

        assertEquals(expected3, kontonummer3);

        long kontonummer4 = bank.sparbuchErstellen(new Kunde());
        long expected4 = 3;

        assertEquals(expected4, kontonummer4);
    }

    //__________________________________________________________________________________________________________________

    //getAlleKonten Test

    @Test
    void getAlleKontenTest() {
        bank.girokontoErstellen(kunde1);
        bank.sparbuchErstellen(kunde2);
        String expected = bank.getAlleKonten();
        assertEquals(expected, bank.getAlleKonten());
    }

    //__________________________________________________________________________________________________________________

    //getAlleKontonummern Test

    @Test
    void getAlleKontonummern() {
        long kontonummer = bank.girokontoErstellen(kunde1);
        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        assertEquals(2, bank.getAlleKontonummern().size());

        assertTrue(bank.getAlleKontonummern().contains(kontonummer));
        assertTrue(bank.getAlleKontonummern().contains(kontonummer2));
    }

    @Test
    void getAlleKontonummern_mitLoeschen_Test() {
        long kontonummer = bank.girokontoErstellen(kunde1);
        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        assertEquals(2, bank.getAlleKontonummern().size());

        assertTrue(bank.getAlleKontonummern().contains(kontonummer));
        assertTrue(bank.getAlleKontonummern().contains(kontonummer2));

        bank.kontoLoeschen(kontonummer);
        assertEquals(1, bank.getAlleKontonummern().size());

        assertFalse(bank.getAlleKontonummern().contains(kontonummer));
        assertTrue(bank.getAlleKontonummern().contains(kontonummer2));
    }

    //__________________________________________________________________________________________________________________

    //geldAbheben Tests

    @Test
    void geldAbheben_normal_true() throws UngueltigeKontonummerException, GesperrtException {
        //Girokonto
        long kontonummer = bank.girokontoErstellen(kunde1);
        bank.geldEinzahlen(kontonummer, 1000.0);
        boolean result = bank.geldAbheben(kontonummer, 500.0);
        assertTrue(result);
        assertEquals(500.0, bank.getKontostand(kontonummer));

        //Sparbuch
        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        bank.geldEinzahlen(kontonummer2, 1000.0);
        boolean result2 = bank.geldAbheben(kontonummer2, 500.0);
        assertTrue(result2);
        assertEquals(500.0, bank.getKontostand(kontonummer2));
    }

    @Test
    void geldAbheben_normal_false() throws UngueltigeKontonummerException, GesperrtException {
        //Girokonto
        long kontonummer = bank.girokontoErstellen(kunde1);
        boolean result = bank.geldAbheben(kontonummer, 5000.0);
        assertFalse(result);
        assertEquals(0.0, bank.getKontostand(kontonummer));

        //Sparbuch
        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        boolean result2 = bank.geldAbheben(kontonummer2, 5000.0);
        assertFalse(result2);
        assertEquals(0.0, bank.getKontostand(kontonummer2));
    }

    @Test
    void geldAbheben_exceptions() {
        //Girokonto
        long kontonummer = bank.girokontoErstellen(kunde1);
        long kontoGesperrt = bank.girokontoErstellen(kunde2);
        bank.getKontenListe().get(kontoGesperrt).sperren();

        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldAbheben(999, 500.0));
        assertThrows(GesperrtException.class, () -> bank.geldAbheben(kontoGesperrt, 500.0));
        assertThrows(IllegalArgumentException.class, () -> bank.geldAbheben(kontonummer, -500.0));

        //Sparbuch
        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        long kontoGesperrt2 = bank.sparbuchErstellen(kunde2);
        bank.getKontenListe().get(kontoGesperrt2).sperren();

        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldAbheben(999, 500.0));
        assertThrows(GesperrtException.class, () -> bank.geldAbheben(kontoGesperrt2, 500.0));
        assertThrows(IllegalArgumentException.class, () -> bank.geldAbheben(kontonummer2, -500.0));
    }

    //__________________________________________________________________________________________________________________

    //geldEinzahlen Tests

    @Test
    void geldEinzahlen_normal() throws UngueltigeKontonummerException {
        //Girokonto
        long kontonummer = bank.girokontoErstellen(kunde1);
        bank.geldEinzahlen(kontonummer, 1000.0);
        assertEquals(1000.0, bank.getKontostand(kontonummer));

        //Sparbuch
        long kontonummer2 = bank.sparbuchErstellen(kunde2);
        bank.geldEinzahlen(kontonummer2, 1000.0);
        assertEquals(1000.0, bank.getKontostand(kontonummer2));
    }

    @Test
    void geldEinzahlen_exceptions() {
        long kontonummer = bank.girokontoErstellen(kunde1);
        long kontonummer2 = bank.sparbuchErstellen(kunde2);

        //Girokonto
        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldEinzahlen(999, 500.0));
        assertThrows(IllegalArgumentException.class, () -> bank.geldEinzahlen(kontonummer, -500.0));

        //Sparbuch
        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldEinzahlen(999, 500.0));
        assertThrows(IllegalArgumentException.class, () -> bank.geldEinzahlen(kontonummer2, -500.0));
    }

    //__________________________________________________________________________________________________________________

    //kontoLoeschen Tests

    @Test
    void kontoLoeschen_withValidKontonummer_shouldRemoveKonto() {
        long kontonummer = bank.girokontoErstellen(kunde1);
        assertTrue(bank.kontoLoeschen(kontonummer));
        assertFalse(bank.getAlleKontonummern().contains(kontonummer));
    }

    @Test
    void kontoLoeschen_withInvalidKontonummer_shouldReturnFalse() {
        assertFalse(bank.kontoLoeschen(999));
    }

    //__________________________________________________________________________________________________________________

    //getKontostand Tests

    @Test
    void getKontostandTrueTest() throws UngueltigeKontonummerException {
        long kontonummer = bank.girokontoErstellen(kunde1);
        bank.geldEinzahlen(kontonummer, 1000.0);
        assertEquals(1000.0, bank.getKontostand(kontonummer));
    }

    @Test
    void getKontostandFalseTest() {
        assertThrows(UngueltigeKontonummerException.class, () -> bank.getKontostand(999));
    }

    //__________________________________________________________________________________________________________________

    //geldUeberweisen Tests

    @Test
    void geldUeberweisen_normal_true_Test() throws GesperrtException, UngueltigeKontonummerException, NichtUeberweisungsfaehigException {
        long vonKontonummer = bank.girokontoErstellen(kunde1);
        long nachKontonummer = bank.girokontoErstellen(kunde2);
        bank.geldEinzahlen(vonKontonummer, 1000.0);

        boolean result = bank.geldUeberweisen(vonKontonummer, nachKontonummer, 500.0, "Überweisungstest");

        assertTrue(result);
        assertEquals(500.0, bank.getKontostand(vonKontonummer));
        assertEquals(500.0, bank.getKontostand(nachKontonummer));
    }

    @Test
    void geldUeberweisen_normal_false_Test() throws GesperrtException, UngueltigeKontonummerException, NichtUeberweisungsfaehigException {
        long vonKontonummer = bank.girokontoErstellen(kunde1);
        long nachKontonummer = bank.girokontoErstellen(kunde2);

        boolean result = bank.geldUeberweisen(vonKontonummer, nachKontonummer, 5000.0, "Überweisungstest");

        assertFalse(result);
        assertEquals(0.0, bank.getKontostand(vonKontonummer));
        assertEquals(0.0, bank.getKontostand(nachKontonummer));
    }

    @Test
    void geldUeberweisen_exception_Test() {
        //UngueltigeKontonummerException
        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldUeberweisen(999, 1, 500.0, "Überweisungstest"));

        //NichtUeberweisungsfaehigException
        long vonKontonummer = bank.girokontoErstellen(kunde1);
        long nachKontonummer = bank.sparbuchErstellen(kunde2);

        assertThrows(NichtUeberweisungsfaehigException.class, () -> bank.geldUeberweisen(vonKontonummer, nachKontonummer, 500.0, "Überweisungstest"));

        //GesperrtException
        long gesperrteKontonummer = bank.girokontoErstellen(kunde1);
        bank.getKontenListe().get(gesperrteKontonummer).sperren();

        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(gesperrteKontonummer, 1, 500.0, "Überweisungstest"));

    }
}