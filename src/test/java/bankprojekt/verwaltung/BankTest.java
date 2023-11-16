package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankTest {

    private Bank bank;
    private Konto vonKontoMock, nachKontoMock;
    private Kunde dummy;

    @BeforeEach
    void setUp() {
        bank = new Bank(12345678); // Erstellt eine Bank mit einer fiktiven Bankleitzahl

        // Erstellen von Mock-Objekten für die Konten
        vonKontoMock = mock(Girokonto.class);
        nachKontoMock = mock(Girokonto.class);

        // Erstellen eines Dummy-Kunden
        dummy = mock(Kunde.class);

        when(dummy.getName()).thenReturn("Max Muster");


        // Definieren des Verhaltens für getInhaber() der Mock-Objekte
        when(vonKontoMock.getInhaber()).thenReturn(dummy);
        when(nachKontoMock.getInhaber()).thenReturn(dummy);
    }

    @Test
    void testGeldUeberweisenErfolgreich() throws UngueltigeKontonummerException, NichtUeberweisungsfaehigException, GesperrtException {
        // Vorbereiten der Testdaten
        long vonKontonr = 1, nachKontonr = 2;
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        bank.mockEinfuegen(vonKontoMock);
        bank.mockEinfuegen(nachKontoMock);
//        bank.getKontenListe().put(vonKontonr, vonKontoMock);
//        bank.getKontenListe().put(nachKontonr, nachKontoMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when(((Ueberweisungsfaehig) vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenReturn(true);

        // Durchführen des Tests
        boolean result = bank.geldUeberweisen(vonKontonr, nachKontonr, betrag, verwendungszweck);

        // Überprüfen der Ergebnisse
        assertTrue(result, "Überweisung sollte erfolgreich sein");
        verify((Ueberweisungsfaehig) vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify((Ueberweisungsfaehig) nachKontoMock).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);
    }

    @Test
    void testGeldUeberweisenFehlgeschlagen() throws UngueltigeKontonummerException, NichtUeberweisungsfaehigException, GesperrtException {
        // Vorbereiten der Testdaten
        long vonKontonr = 1, nachKontonr = 2;
        double betrag = 100.0;
        String verwendungszweck = "Testüberweisung";

        // Hinzufügen der Mock-Konten zur Bank
        bank.mockEinfuegen(vonKontoMock);
        bank.mockEinfuegen(nachKontoMock);

        // Definieren des Verhaltens der Mock-Objekte
        when(vonKontoMock.getKontonummer()).thenReturn(vonKontonr);
        when(nachKontoMock.getKontonummer()).thenReturn(nachKontonr);
        when(((Ueberweisungsfaehig) vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck)).thenReturn(false);

        // Durchführen des Tests
        boolean result = bank.geldUeberweisen(vonKontonr, nachKontonr, betrag, verwendungszweck);

        // Überprüfen der Ergebnisse
        assertFalse(result, "Überweisung sollte fehlschlagen");
        verify((Ueberweisungsfaehig) vonKontoMock).ueberweisungAbsenden(betrag, nachKontoMock.getInhaber().getName(), nachKontonr, bank.getBankleitzahl(), verwendungszweck);
        verify((Ueberweisungsfaehig) nachKontoMock, never()).ueberweisungEmpfangen(betrag, vonKontoMock.getInhaber().getName(), vonKontonr, bank.getBankleitzahl(), verwendungszweck);
    }
}
