package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.controllers.BaseControllerAbstract;
import fit.biepjv.agilediary.jdbc.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class BaseUiControllerAbstract {
    protected Stage stage;
    protected DatabaseConnector dbConnector;

    public BaseUiControllerAbstract(Stage stage, DatabaseConnector dbConnector)
    {
        this.stage = stage;
        this.dbConnector = dbConnector;
    }

    @FXML
    public abstract void initialize();

}
