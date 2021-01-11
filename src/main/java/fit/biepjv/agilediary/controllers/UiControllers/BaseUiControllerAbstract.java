package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.jdbc.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/** <b>Base UI Controller</b>
 * Default "facade" between View and Model.
 * This is an abstract class that describes a basic entity
 */
public abstract class BaseUiControllerAbstract {
    /**
     * Default stage
     */
    protected Stage stage;
    /**
     * Database connector object
     */
    protected DatabaseConnector dbConnector;

    /**
     * Constructor based on stage and db connector
     * @param stage Stage that passed to changed the layout
     * @param dbConnector Database connector object
     */
    public BaseUiControllerAbstract(Stage stage, DatabaseConnector dbConnector) {
        this.stage = stage;
        this.dbConnector = dbConnector;
    }

    /**
     * Method that initializes the JavaFX View page
     */
    @FXML
    public abstract void initialize();

}
