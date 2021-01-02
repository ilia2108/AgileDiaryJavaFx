package fit.biepjv.agilediary.events.handlers;

import fit.biepjv.agilediary.Main;
import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.controllers.UiControllers.CreateController;
import fit.biepjv.agilediary.controllers.UiControllers.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.List;

public abstract class AddIssueEventHandler implements EventHandler<Event> {
    String type;
    MainController mainController;
    Stage stage;

    public AddIssueEventHandler(String type,
                                Stage stage,
                               MainController controller){
        this.type = type;
        this.mainController = controller;
        this.stage = stage;
    }
    public AddIssueEventHandler(String type, MainController controller){
        this.type = type;
        this.mainController = controller;

    }
    public String getType(){
        return type;
    }

    public void handle(Event event){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/CreatePage.fxml"));
        Parent root = null;
        CreateController controller =
                new CreateController(stage, mainController.getDbConnector(), type, mainController);
        loader.setController(controller);
        try {
            root = loader.load();
        }
        catch (Exception ignored){
        }
        Scene scene = new Scene(root, 600,400);

        stage.setScene(scene);
        stage.show();
    }
}
