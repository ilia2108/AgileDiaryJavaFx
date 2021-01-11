package fit.biepjv.agilediary;

import fit.biepjv.agilediary.controllers.UiControllers.MainController;
import fit.biepjv.agilediary.events.handlers.AddIssueEventHandler;

import fit.biepjv.agilediary.jdbc.DatabaseConnector;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is a main class of the application.
 */
public class Main extends javafx.application.Application {

    MainController mainController;
    DatabaseConnector dbConnector;

    @Override
    public void start(Stage stage) throws Exception{
        dbConnector = new DatabaseConnector();
        mainController = new MainController(stage, dbConnector);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/MainPage.fxml"));
        loader.setController(mainController);
        Parent root = loader.load();
        stage.setTitle("Agile Diary");

        Scene scene = new Scene(root, 600,400);
        stage.setScene(scene);
        stage.show();

        AddIssueEventHandler themeAdd =
                new AddIssueEventHandler("theme", stage, mainController) {
            @Override
            public void handle(Event event) {
                super.handle(event);
            }
        };


        mainController.btn_AddDefault.setOnMouseClicked(themeAdd);
    }


    public static void main(String[] args){
        launch(args);
    }
}
