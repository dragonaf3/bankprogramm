package bankprojekt.oberflaeche;

import bankprojekt.verarbeitung.Girokonto;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Eine Oberfläche für ein einzelnes Konto. Man kann einzahlen
 * und abheben und sperren und die Adresse des Kontoinhabers
 * ändern
 *
 * @author Doro
 */
public class KontoOberflaeche extends BorderPane {
    private Text ueberschrift;
    private GridPane anzeige;
    private Text txtNummer;
    /**
     * Anzeige der Kontonummer
     */
    private Text nummer;
    private Text txtStand;
    /**
     * Anzeige des Kontostandes
     */
    private Text stand;
    private Text txtGesperrt;
    /**
     * Anzeige und Änderung des Gesperrt-Zustandes
     */
    private CheckBox gesperrt;
    private Text txtAdresse;
    /**
     * Anzeige und Änderung der Adresse des Kontoinhabers
     */
    private TextArea adresse;
    /**
     * Anzeige von Meldungen über Kontoaktionen
     */
    private Text meldung;
    private HBox aktionen;
    /**
     * Auswahl des Betrags für eine Kontoaktion
     */
    private TextField betrag;
    /**
     * löst eine Einzahlung aus
     */
    private Button einzahlen;
    /**
     * löst eine Abhebung aus
     */
    private Button abheben;
    private Girokonto girokonto;
    private KontoController kontoController;

    /**
     * erstellt die Oberfläche
     */
    public KontoOberflaeche(Girokonto girokonto, KontoController kontoController) {
        this.girokonto = girokonto;
        this.kontoController = kontoController;

        ueberschrift = new Text("Ein Konto verändern");
        ueberschrift.setFont(new Font("Sans Serif", 25));
        BorderPane.setAlignment(ueberschrift, Pos.CENTER);
        this.setTop(ueberschrift);

        anzeige = new GridPane();
        anzeige.setPadding(new Insets(20));
        anzeige.setVgap(10);
        anzeige.setAlignment(Pos.CENTER);

        txtNummer = new Text("Kontonummer:");
        txtNummer.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtNummer, 0, 0);
        nummer = new Text(String.valueOf(girokonto.getKontonummer()));
        nummer.setFont(new Font("Sans Serif", 15));
        GridPane.setHalignment(nummer, HPos.RIGHT);
        anzeige.add(nummer, 1, 0);

        txtStand = new Text("Kontostand:");
        txtStand.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtStand, 0, 1);
        stand = new Text();
        stand.setFont(new Font("Sans Serif", 15));
        GridPane.setHalignment(stand, HPos.RIGHT);
        anzeige.add(stand, 1, 1);

        txtGesperrt = new Text("Gesperrt: ");
        txtGesperrt.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtGesperrt, 0, 2);
        gesperrt = new CheckBox();
        GridPane.setHalignment(gesperrt, HPos.RIGHT);
        anzeige.add(gesperrt, 1, 2);

        txtAdresse = new Text("Adresse: ");
        txtAdresse.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtAdresse, 0, 3);
        adresse = new TextArea();
        adresse.setPrefColumnCount(25);
        adresse.setPrefRowCount(2);
        GridPane.setHalignment(adresse, HPos.RIGHT);
        anzeige.add(adresse, 1, 3);

        meldung = new Text("Willkommen lieber Benutzer");
        meldung.setFont(new Font("Sans Serif", 15));
        meldung.setFill(Color.RED);
        anzeige.add(meldung, 0, 4, 2, 1);

        this.setCenter(anzeige);

        aktionen = new HBox();
        aktionen.setSpacing(10);
        aktionen.setAlignment(Pos.CENTER);

        betrag = new TextField("100.00");
        aktionen.getChildren().add(betrag);
        einzahlen = new Button("Einzahlen");
        aktionen.getChildren().add(einzahlen);
        abheben = new Button("Abheben");
        aktionen.getChildren().add(abheben);

        this.setBottom(aktionen);
    }

    /**
     * Gibt das Text-Objekt für die Anzeige des Kontostands zurück.
     *
     * @return das Text-Objekt für die Anzeige des Kontostands
     */
    public Text getStandText() {
        return this.stand;
    }

    /**
     * Gibt das CheckBox-Objekt für die Anzeige und Änderung des Gesperrt-Zustands zurück.
     *
     * @return das CheckBox-Objekt für die Anzeige und Änderung des Gesperrt-Zustands
     */
    public CheckBox getGesperrtCheckbox() {
        return this.gesperrt;
    }

    /**
     * Gibt das TextArea-Objekt für die Anzeige und Änderung der Adresse des Kontoinhabers zurück.
     *
     * @return das TextArea-Objekt für die Anzeige und Änderung der Adresse des Kontoinhabers
     */
    public TextArea getAdresseTextArea() {
        return this.adresse;
    }

    /**
     * Gibt das Button-Objekt für die Einzahlungsaktion zurück.
     *
     * @return das Button-Objekt für die Einzahlungsaktion
     */
    public Button getEinzahlenButton() {
        return this.einzahlen;
    }

    /**
     * Gibt das Button-Objekt für die Abhebungsaktion zurück.
     *
     * @return das Button-Objekt für die Abhebungsaktion
     */
    public Button getAbhebenButton() {
        return this.abheben;
    }

    /**
     * Gibt das TextField-Objekt für die Auswahl des Betrags für eine Kontoaktion zurück.
     *
     * @return das TextField-Objekt für die Auswahl des Betrags für eine Kontoaktion
     */
    public TextField getBetragTextField() {
        return this.betrag;
    }

    /**
     * Gibt das Text-Objekt für die Anzeige von Meldungen über Kontoaktionen zurück.
     *
     * @return das Text-Objekt für die Anzeige von Meldungen über Kontoaktionen
     */
    public Text getMeldungText() {
        return this.meldung;
    }
}
