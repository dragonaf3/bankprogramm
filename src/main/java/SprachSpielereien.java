import bankprojekt.sprachsteuerung.SprachAuswahl;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * Die Klasse SprachSpielereien demonstriert die Verwendung von Girokonto und Sparbuch mit unterschiedlichen Spracheinstellungen.
 */
public class SprachSpielereien {
    /**
     * Die Hauptmethode erstellt Girokonto und Sparbuch Objekte in Deutsch und Englisch und gibt sie aus.
     * @param args Kommandozeilenargumente, die nicht verwendet werden.
     */
    public static void main(String[] args) {
        // Erstellt ein Girokonto und ein Sparbuch in der Standard Sprache (Deutsch)
        Girokonto girokonto = new Girokonto();
        Sparbuch sparbuch = new Sparbuch();

        // Gibt die Informationen des Girokontos und des Sparbuchs in Deutsch aus
        System.out.println(girokonto);
        System.out.println(sparbuch);

        // Setzt die Sprache auf Englisch
        SprachAuswahl.auswahlSprache("EN");

        // Erstellt ein Girokonto und ein Sparbuch in Englisch
        Girokonto girokontoEN = new Girokonto();
        Sparbuch sparbuchEN = new Sparbuch();

        // Gibt die Informationen des Girokontos und des Sparbuchs in Englisch aus
        System.out.println(girokontoEN);
        System.out.println(sparbuchEN);
    }
}