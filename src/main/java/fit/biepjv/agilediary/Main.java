package fit.biepjv.agilediary;

import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.events.handlers.AddIssueEventHandler;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends javafx.application.Application {

    MainController mainController;




    @Override
    public void start(Stage stage) throws Exception{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainPage.fxml"));
        loader.setController(new MainController());
        Parent root = loader.load();

        mainController = loader.getController();

        stage.setTitle("Agile Diary");

        Scene scene = new Scene(root, 600,400);
        stage.setScene(scene);
        stage.show();


        mainController.btn_AddDefault.setOnMouseClicked(new AddIssueEventHandler(
                "theme", stage, mainController) {
            @Override
            public void handle(Event event) {
                super.handle(event);
            }
        });
    }


    public static void main(String[] args){
        launch(args);
    }
}
