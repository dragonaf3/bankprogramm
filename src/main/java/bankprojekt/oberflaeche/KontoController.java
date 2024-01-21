package bankprojekt.oberflaeche;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Der KontoController ist verantwortlich für die Verwaltung der Kontooberfläche und der Interaktionen mit dem Girokonto.
 */
public class KontoController {

    private KontoOberflaeche oberflaeche;
    private Girokonto girokonto;
    private Stage stage;

    /**
     * Konstruktor für den KontoController. Initialisiert ein Girokonto und die Kontooberfläche.
     *
     * @param stage Die Bühne, auf der die Kontooberfläche angezeigt wird.
     */
    public KontoController(Stage stage) {
        this.girokonto = new Girokonto(new Kunde("Max", "Muster", "Maxstraße", LocalDate.now()), 12345, 5000);
        this.oberflaeche = new KontoOberflaeche(girokonto, this);
        setupBindingsAndListeners();

        this.stage = stage;
        this.stage.setScene(new Scene(this.oberflaeche, 900, 800));
        this.stage.show();
    }

    /**
     * Setzt die Bindungen und Listener für die Kontooberfläche auf.
     */
    private void setupBindingsAndListeners() {
        oberflaeche.getStandText().textProperty().bind(Bindings.createStringBinding(
                () -> String.format("%.2f", girokonto.kontostandProperty().get()),
                girokonto.kontostandProperty()
        ));
        oberflaeche.getStandText().fillProperty().bind(Bindings.when(girokonto.imPlusProperty()).then(Color.GREEN).otherwise(Color.RED));

        oberflaeche.getAdresseTextArea().textProperty().bindBidirectional(girokonto.getInhaber().adresseProperty());

        oberflaeche.getEinzahlenButton().setOnAction(e -> handleEinzahlung());
        oberflaeche.getAbhebenButton().setOnAction(e -> handleAbhebung());
        oberflaeche.getGesperrtCheckbox().setOnAction(e -> handleGesperrt());
    }

    /**
     * Behandelt das Sperren und Entsperren des Girokontos.
     */
    private void handleGesperrt() {
        if (oberflaeche.getGesperrtCheckbox().isSelected()) {
            girokonto.sperren();
            oberflaeche.getMeldungText().setText("Konto gesperrt!");
        } else {
            girokonto.entsperren();
            oberflaeche.getMeldungText().setText("Konto entsperrt!");
        }
    }

    /**
     * Behandelt die Einzahlung auf das Girokonto.
     */
    private void handleEinzahlung() {
        try {
            double betrag = Double.parseDouble(oberflaeche.getBetragTextField().getText());
            girokonto.einzahlen(betrag);
            oberflaeche.getMeldungText().setText("Einzahlung erfolgreich!");
        } catch (IllegalArgumentException e) {
            oberflaeche.getMeldungText().setText("Ungültiger Betrag!");
        }
    }

    /**
     * Behandelt die Abhebung vom Girokonto.
     */
    private void handleAbhebung() {
        try {
            double betrag = Double.parseDouble(oberflaeche.getBetragTextField().getText());
            if (girokonto.abheben(betrag)) {
                oberflaeche.getMeldungText().setText("Abhebung erfolgreich!");
            } else {
                oberflaeche.getMeldungText().setText("Kontostand nicht ausreichend!");
            }

        } catch (NumberFormatException e) {
            oberflaeche.getMeldungText().setText("Ungültiger Betrag!");
        } catch (GesperrtException e) {
            oberflaeche.getMeldungText().setText("Konto gesperrt!");
        }
    }

}