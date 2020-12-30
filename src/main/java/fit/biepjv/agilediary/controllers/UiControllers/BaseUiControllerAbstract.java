package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.controllers.BaseControllerAbstract;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class BaseUiControllerAbstract {
    protected Stage stage;

    public BaseUiControllerAbstract(Stage stage){
        this.stage = stage;
    }

    @FXML
    public abstract void initialize();

}
