import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.fabrik.GirokontoFabrik;
import bankprojekt.verarbeitung.fabrik.SparbuchFabrik;
import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.NichtUeberweisungsfaehigException;
import bankprojekt.verwaltung.UngueltigeKontonummerException;

/**
 * Die Klasse KontoBenachrichtigungSpielerei spielt mit der Konto Benachrichtigungsfunktion.
 * Sie erstellt eine Bank und führt verschiedene Operationen auf den Konten durch.
 */
public class KontoBenachrichtigungSpielerei {

    /**
     * Die Hauptmethode des Programms.
     * Sie erstellt eine Bank und führt verschiedene Operationen auf den Konten durch.
     *
     * @param args Die Befehlszeilenargumente.
     * @throws UngueltigeKontonummerException    Wenn eine ungültige Kontonummer angegeben wird.
     * @throws GesperrtException                 Wenn ein Konto gesperrt ist.
     * @throws NichtUeberweisungsfaehigException Wenn ein Konto nicht überweisungsfähig ist.
     */
    public static void main(String[] args) throws UngueltigeKontonummerException, GesperrtException, NichtUeberweisungsfaehigException {
        Bank bank = new Bank(12345);

        Long konto1 = bank.kontoErstellen(new GirokontoFabrik(), new Kunde());
        Long konto2 = bank.kontoErstellen(new GirokontoFabrik(), new Kunde());
        Long konto3 = bank.kontoErstellen(new SparbuchFabrik(), new Kunde());

        bank.geldEinzahlen(konto1, 1000);
        bank.geldEinzahlen(konto2, 1000);
        bank.geldEinzahlen(konto3, 1000);

        bank.geldUeberweisen(konto1, konto2, 500, "Bitte");

        bank.geldAbheben(konto3, 900);
    }
}