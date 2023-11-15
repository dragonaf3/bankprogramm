package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankTest {

    private Bank bank;
    @Mock
    private Kunde kunde1;
    @Mock
    private Konto kontoUeberweisungsfähig;
    @Mock
    private Kunde kunde2;
    @Mock
    private Konto kontoUeberweisungsfähig2;
    @Mock
    private Kunde kunde3;
    @Mock
    private Konto kontoNichtUeberweisungsfähig;

    @BeforeEach
    void setUp() {
        bank = new Bank(12345); // Bankleitzahl als Beispielwert

        // Erstellen von Mocks für Konto-Objekte
        kontoUeberweisungsfähig = mock(Girokonto.class);
        kontoUeberweisungsfähig2 = mock(Girokonto.class);
        kontoNichtUeberweisungsfähig = mock(Konto.class);

        // Erstellen von Mocks für Kunden-Objekte
        kunde1 = mock(Kunde.class);
        kunde2 = mock(Kunde.class);
        kunde3 = mock(Kunde.class);

        when(kunde1.getName()).thenReturn("Empfaenger");
        when(kunde2.getName()).thenReturn("Sender");

        // Fügen Sie Mock-Konten in die Bank ein (dies erfordert möglicherweise eine Änderung in der Bankklasse, um dies zu ermöglichen)
        bank.mockEinfuegen(kontoUeberweisungsfähig);
        bank.mockEinfuegen(kontoUeberweisungsfähig2);
        bank.mockEinfuegen(kontoNichtUeberweisungsfähig);

        when(kontoUeberweisungsfähig.getKontonummer()).thenReturn(1L);
        when(kontoUeberweisungsfähig2.getKontonummer()).thenReturn(2L);
        when(kontoNichtUeberweisungsfähig.getInhaber()).thenReturn(mock(kunde1));
        when(kontoUeberweisungsfähig.getInhaber()).thenReturn(mock(kunde2));


    }

    @Test
    void geldUeberweisen_normal_true_Test() throws Exception {
        when(((Ueberweisungsfaehig)kontoUeberweisungsfähig).ueberweisungAbsenden(500.0,"Empfaenger",2,12345,"Überweisungstest")).thenReturn(true);
        doNothing().when((Ueberweisungsfaehig)kontoUeberweisungsfähig2).ueberweisungEmpfangen(500.0,"Sender",1,12345,"Überweisungstest");

        boolean result = bank.geldUeberweisen(1, 2, 500.0, "Überweisungstest");

        assertTrue(result);
        verify((Ueberweisungsfaehig)kontoUeberweisungsfähig).ueberweisungAbsenden(500,"Empfaenger",2,12345,"Überweisungstest");
        verify((Ueberweisungsfaehig)kontoUeberweisungsfähig2).ueberweisungEmpfangen(500,"Sender",1,12345,"Überweisungstest");
    }

//    @Test
//    void geldUeberweisen_normal_false_Test() throws Exception {
//        when(kontoUeberweisungsfähig.abheben(5000.0)).thenReturn(false);
//
//        boolean result = bank.geldUeberweisen(1, 2, 5000.0, "Überweisungstest");
//
//        assertFalse(result);
//        verify(kontoUeberweisungsfähig).abheben(5000.0);
//        verify(kontoNichtUeberweisungsfähig, never()).einzahlen(5000.0);
//    }
//
//    @Test
//    void geldUeberweisen_exception_Test() {
//        // UngueltigeKontonummerException
//        assertThrows(UngueltigeKontonummerException.class, () -> bank.geldUeberweisen(999, 1, 500.0, "Überweisungstest"));
//
//        // NichtUeberweisungsfaehigException
//        assertThrows(NichtUeberweisungsfaehigException.class, () -> bank.geldUeberweisen(1, 3, 500.0, "Überweisungstest"));
//
//        // GesperrtException
//        when(kontoUeberweisungsfähig.istGesperrt()).thenReturn(true);
//        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(1, 2, 500.0, "Überweisungstest"));
//    }
}
