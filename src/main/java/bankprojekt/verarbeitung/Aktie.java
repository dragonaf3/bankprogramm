package bankprojekt.verarbeitung;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Die Klasse Aktie repräsentiert eine Aktie mit einem Namen, einer WKN und einem Kurs.
 * Der Kurs der Aktie wird regelmäßig aktualisiert.
 */
public class Aktie {
    private String name;
    private String wkn;
    private double kurs;
    private ScheduledExecutorService executor;

    /**
     * Erstellt eine neue Aktie mit einem Namen, einer WKN und einem Startkurs.
     * Startet auch die regelmäßige Kursaktualisierung.
     *
     * @param name      Der Name der Aktie
     * @param wkn       Die WKN der Aktie
     * @param startkurs Der Startkurs der Aktie
     * @throws IllegalArgumentException wenn der Name oder die WKN leer sind oder der Startkurs kleiner oder gleich 0 ist
     * @throws NullPointerException     wenn der Name oder die WKN null sind
     */
    public Aktie(String name, String wkn, double startkurs) throws IllegalArgumentException, NullPointerException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name darf nicht null oder leer sein.");
        }
        if (wkn == null || wkn.trim().isEmpty()) {
            throw new IllegalArgumentException("WKN darf nicht null oder leer sein.");
        }
        if (startkurs <= 0) {
            throw new IllegalArgumentException("Startkurs muss größer als 0 sein.");
        }

        this.name = name;
        this.wkn = wkn;
        this.kurs = startkurs;

        this.executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::updateKurs, 0, 1, TimeUnit.SECONDS);
    }


    /**
     * Gibt den aktuellen Kurs der Aktie zurück.
     *
     * @return Der aktuelle Kurs der Aktie
     */
    public double getKurs() {
        return kurs;
    }

    /**
     * Gibt die WKN der Aktie zurück.
     *
     * @return Die WKN der Aktie
     */
    public String getWkn() {
        return wkn;
    }

    /**
     * Stoppt die regelmäßige Kursaktualisierung. Dient dem testen in AktienSpielereien.
     */
    public void stopKurs() {
        executor.shutdown();
    }

    /**
     * Aktualisiert den Kurs der Aktie.
     * Der neue Kurs ist ein zufälliger Wert zwischen -3% und 3% des aktuellen Kurses.
     * Stellt sicher, dass der Kurs immer über 0 bleibt.
     */
    private void updateKurs() {
        // Generiert zufälligen double Wert zwischen -3 und 3.
        // Genau 3 wird nicht erreicht, aber 2,999.. was ausreichen sollte.
        double prozentAenderung = Math.random() * 6 - 3;

        double neuerKurs = kurs * (1 + prozentAenderung / 100);

        if (neuerKurs <= 0) {
            neuerKurs = 0.01;  // Setzt den Kurs auf einen minimalen positiven Wert
        }

        this.kurs = neuerKurs;
    }

}