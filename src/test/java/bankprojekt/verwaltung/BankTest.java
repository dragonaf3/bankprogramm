package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testklasse für die Klasse Bank
 */
class BankTest {

    //Bank
    private Bank bank;
    //Konten welche nicht überweisungsfähig sind
    private Konto vonKontoNichtUeberweisungsfaehigMock, nachKontoNichtUeberweisungsfaehigMock;
    //Konten welche überweisungsfähig sind
    private Girokonto vonKontoMock, nachKontoMock, vonKontoGesperrtMock, nachKontoGesperrtMock;
    private Kunde dummy, dummy2, dummy3, dummy4, dummy5, dummy6;

    /**
     * Initialisierung der Mock-Objekte und der Bank vor jedem Test
     */
    @BeforeEach
    void setUp() {
        bank = new Bank(12345);

        // Erstellen von Mock-Objekten für die Konten
        vonKontoNichtUeberweisungsfaehigMock = mock(Konto.class);
        nachKontoNichtUeberweisungsfaehigMock = mock(Konto.class);
        vonKontoMock = mock(Girokonto.class);
        nachKontoMock = mock(Girokonto.class);
        vonKontoGesperrtMock = mock(Girokonto.class);
        nachKontoGesperrtMock = mock(Girokonto.class);

        // Erstellen von Dummy-Kunden
        dummy = mock(Kunde.class);
        dummy2 = mock(Kunde.class);
        dummy3 = mock(Kunde.class);
        dummy4 = mock(Kunde.class);
        dummy5 = mock(Kunde.class);
        dummy6 = mock(Kunde.class);

        when(dummy.getName()).thenReturn("Max Muster");
        when(dummy2.getName()).thenReturn("Mara Müster");
        when(dummy3.getName()).thenReturn("Maxes Kuster");
        when(dummy4.getName()).thenReturn("Mata Lüster");
        when(dummy5.getName()).thenReturn("Mux Muxter");
        when(dummy6.getName()).thenReturn("Mux Müxter");


        // Definieren des Verhaltens für getInhaber() der Mock-Objekte
        when(vonKontoMock.getInhaber()).thenReturn(dummy);
        when(nachKontoMock.getInhaber()).thenReturn(dummy2);
        when(vonKontoNichtUeberweisungsfaehigMock.getInhaber()).thenReturn(dummy3);
        when(nachKontoNichtUeberweisungsfaehigMock.getInhaber()).thenReturn(dummy4);
        when(vonKontoGesperrtMock.getInhaber()).thenReturn(dummy5);
        when(nachKontoGesperrtMock.getInhaber()).thenReturn(dummy6);

        when(vonKontoGesperrtMock.isGesperrt()).thenReturn(true);
        when(nachKontoGesperrtMock.isGesperrt()).thenReturn(true);

    }

    // Tests zur Methode geldUeberweisen()

    /**
     * Testet die Methode geldUeberweisen() im Normalfall, wenn diese erfolgreich ist
     *
     * @throws UngueltigeKontonummerException
     * @throws NichtUeberweisungsfaehigException
     * @throws GesperrtException
     */
    @Test
    void testGeldUeberweisenErfolgreich() throws UngueltigeKontonummerException, NichtUeberweisungsfaehigException, GesperrtException {
        // Vorbereiten der Testdaten
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        long vonKontonr = bank.mockEinfuegen(vonKontoMock);
        long nachKontonr = bank.mockEinfuegen(nachKontoMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when((vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenReturn(true);

        // Durchführen des Tests
        boolean result = bank.geldUeberweisen(vonKontonr, nachKontonr, betrag, verwendungszweck);

        // Überprüfen der Ergebnisse
        assertTrue(result, "Überweisung sollte erfolgreich sein");
        verify(vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify(nachKontoMock).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);
    }

    /**
     * Testet die Methode geldUeberweisen() im Normalfall, wenn diese nicht erfolgreich ist
     *
     * @throws UngueltigeKontonummerException
     * @throws NichtUeberweisungsfaehigException
     * @throws GesperrtException
     */
    @Test
    void testGeldUeberweisenFehlgeschlagen() throws UngueltigeKontonummerException, NichtUeberweisungsfaehigException, GesperrtException {
        // Vorbereiten der Testdaten
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        long vonKontonr = bank.mockEinfuegen(vonKontoMock);
        long nachKontonr = bank.mockEinfuegen(nachKontoMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when((vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenReturn(false);

        // Durchführen des Tests
        boolean result = bank.geldUeberweisen(vonKontonr, nachKontonr, betrag, verwendungszweck);

        // Überprüfen der Ergebnisse
        assertFalse(result, "Überweisung sollte fehlschlagen");
        verify(vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify(nachKontoMock, never()).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);
    }

    /**
     * Testet die Methode geldUeberweisen() im Fehlerfall, wenn eine ungültige Kontonummer übergeben wird
     *
     * @throws GesperrtException
     */
    @Test
    void testGeldUeberweisenFehlerfallUngueltigeKontonummer() throws GesperrtException {
        // Vorbereiten der Testdaten
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        long vonKontonr = bank.mockEinfuegen(vonKontoMock);
        long nachKontonr = bank.mockEinfuegen(nachKontoMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when((vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenReturn(false);

        //Durchführen des Tests und Überprüfen
        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldUeberweisen(3, nachKontonr, betrag, verwendungszweck));
        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldUeberweisen(vonKontonr, 3, betrag, verwendungszweck));
        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldUeberweisen(3, 4, betrag, verwendungszweck));

        //Überprüfen der Ergebnisse
        verify(vonKontoMock, never()).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify(nachKontoMock, never()).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);

    }

    /**
     * Testet die Methode geldUeberweisen() im Fehlerfall, wenn ein nicht überweisungsfähiges Konto übergeben wird
     *
     * @throws GesperrtException
     */
    @Test
    void testGeldUeberweisenFehlerfallNichtUeberweisungsfaehigException() throws GesperrtException {
        // Vorbereiten der Testdaten
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        long vonKontonr = bank.mockEinfuegen(vonKontoMock);
        long nachKontonr = bank.mockEinfuegen(nachKontoMock);
        long vonKontoNichtUeberweisungsfaehig1 = bank.mockEinfuegen(vonKontoNichtUeberweisungsfaehigMock);
        long nachKontoNichtUeberweisungsfaehig2 = bank.mockEinfuegen(nachKontoNichtUeberweisungsfaehigMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when(vonKontoNichtUeberweisungsfaehigMock.getKontonummer()).thenReturn(vonKontoNichtUeberweisungsfaehig1);
        when(nachKontoNichtUeberweisungsfaehigMock.getKontonummer()).thenReturn(nachKontoNichtUeberweisungsfaehig2);
        when((vonKontoMock).ueberweisungAbsenden(betrag, nachKontoNichtUeberweisungsfaehigMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenReturn(false);

        //Durchführen des Tests und Überprüfen
        assertThrows(NichtUeberweisungsfaehigException.class, () -> bank.geldUeberweisen(vonKontoNichtUeberweisungsfaehig1, nachKontonr, betrag, verwendungszweck));
        assertThrows(NichtUeberweisungsfaehigException.class, () -> bank.geldUeberweisen(vonKontonr, nachKontoNichtUeberweisungsfaehig2, betrag, verwendungszweck));
        assertThrows(NichtUeberweisungsfaehigException.class, () -> bank.geldUeberweisen(vonKontoNichtUeberweisungsfaehig1, nachKontoNichtUeberweisungsfaehig2, betrag, verwendungszweck));

        //Überprüfen der Ergebnisse
        verify(vonKontoMock, never()).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify(nachKontoMock, never()).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);
    }

    /**
     * Testet die Methode geldUeberweisen() im Fehlerfall, wenn ein gesperrtes Konto übergeben wird
     *
     * @throws GesperrtException
     */
    @Test
    void testGeldUeberweisenFehlerfallGesperrtException() throws GesperrtException {
        // Vorbereiten der Testdaten
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        long vonKontonr = bank.mockEinfuegen(vonKontoMock);
        long nachKontonr = bank.mockEinfuegen(nachKontoMock);
        long vonKontoGesperrtNr = bank.mockEinfuegen(vonKontoGesperrtMock);
        long nachKontoGesperrtNr = bank.mockEinfuegen(nachKontoGesperrtMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when(vonKontoGesperrtMock.getKontonummer()).thenReturn(vonKontoGesperrtNr);
        when(nachKontoGesperrtMock.getKontonummer()).thenReturn(nachKontoGesperrtNr);
        when(vonKontoGesperrtMock.ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenThrow(new GesperrtException(vonKontoGesperrtNr));
        when(vonKontoGesperrtMock.ueberweisungAbsenden(betrag, nachKontoGesperrtMock.getInhaber().getName(), nachKontoGesperrtNr, bank.getBankleitzahl(), verwendungszweck)).thenThrow(new GesperrtException(nachKontoGesperrtNr));


        //Durchführen des Tests und Überprüfen
        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(vonKontoGesperrtNr, nachKontonr, betrag, verwendungszweck));
        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(vonKontoGesperrtNr, nachKontoGesperrtNr, betrag, verwendungszweck));

        //Da laut Schnittstelle eine Überweisung an einem gesperrten Konto möglich ist
//        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(vonKontonr, nachKontoGesperrtNr, betrag, verwendungszweck));

        //Überprüfen der Ergebnisse
        verify(nachKontoMock, never()).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify(nachKontoGesperrtMock, never()).ueberweisungEmpfangen(betrag, vonKontoGesperrtMock.getInhaber().getName(), vonKontoGesperrtNr, bank.getBankleitzahl(), verwendungszweck);
    }

    //Tests zur Methode  kontoLoeschen()

    /**
     * Testet die Methode kontoLoeschen() bei einem true Fall
     */
    @Test
    void testKontoLoeschenTrue() {
        //Vorbereiten der Testdaten
        long kontonr = bank.mockEinfuegen(vonKontoMock);
        when(vonKontoMock.getKontonummer()).thenReturn(kontonr);

        //Durchführen des Tests
        boolean result = bank.kontoLoeschen(kontonr);

        //Überprüfen der Ergebnisse
        assertTrue(result, "Konto sollte gelöscht werden");
        assertFalse(bank.getKontenListe().containsKey(kontonr), "Konto sollte nicht mehr in der Kontenliste sein");
    }

    /**
     * Testet die Methode kontoLoeschen() bei einem false Fall
     */
    @Test
    void testKontoLoeschenFalse() {
        //Vorbereiten der Testdaten
        long kontonr = bank.mockEinfuegen(vonKontoMock);
        when(vonKontoMock.getKontonummer()).thenReturn(kontonr);

        //Durchführen des Tests
        boolean result = bank.kontoLoeschen(2);

        //Überprüfen der Ergebnisse
        assertFalse(result, "Konto sollte nicht gelöscht werden");
        assertTrue(bank.getKontenListe().containsKey(kontonr), "Konto sollte noch in der Kontenliste sein");
    }
}
