import bankprojekt.oberflaeche.KontoController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Die Klasse OberflaechenSpielerei ist eine JavaFX-Anwendung, die eine Benutzeroberfläche für das KontoController-Objekt bereitstellt.
 */
public class OberflaechenSpielerei extends Application {

    /**
     * Die Methode start wird beim Starten der Anwendung aufgerufen.
     * Sie erstellt ein neues KontoController-Objekt und übergibt die Bühne (Stage) an den KontoController.
     *
     * @param stage Die Hauptbühne für diese Anwendung.
     * @throws Exception Wenn beim Starten der Anwendung ein Fehler auftritt.
     */
    @Override
    public void start(Stage stage) throws Exception {
        KontoController kontoController = new KontoController(stage);
    }
}