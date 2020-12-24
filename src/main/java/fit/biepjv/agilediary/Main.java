package fit.biepjv.agilediary;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.events.handlers.AddIssueEventHandler;
import fit.biepjv.agilediary.models.*;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Struct;
import java.util.List;


public class Main extends javafx.application.Application {

    MainController mainController;




    @Override
    public void start(Stage stage) throws Exception{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainPage.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        stage.setTitle("Agile Diary");

        mainController.btn_AddDefault.setOnMouseClicked(new AddIssueEventHandler("initiative") {
            @Override
            public void handle(Event event) {
                //todo: call other page and finish
            }
        });

        Boolean noElems =
                mainController.controllersList
                        .get("initiative")
                        .getIncludedIssuesList()
                        .size() == 0;
        mainController.grid_Issues.setVisible(!noElems);
        mainController.grid_themes.setVisible(!noElems);
        mainController.grid_NavigationButtons.setVisible(!noElems);
        mainController.grid_noContent.setVisible(noElems);

        Scene scene = new Scene(root, 600,400);
        stage.setScene(scene);
        stage.show();




        //test
//
//        Theme theme = new Theme.ThemeBuilder()
//                .name("Theme1")
//                .description("")
//                .build();
//        Epic epic= (Epic) new Epic.EpicBuilder()
//                .name("Epic1")
//                .description("epic1 epic1 e pic 1")
//                .addTheme(theme)
//                .priority(2)
//                .addAssignee("User1")
//                .addAssignee("User2")
//                .build();
//        Initiative initiative = ((Initiative.InitiativeBuilder)new Initiative.InitiativeBuilder()
//                .name("Ini1")
//                .description("description of ini1")
//                .addAssignee("User1")
//                .addTheme(theme))
//                .addEpic(epic)
//                .build();
//        ((IssueControllerAbstract)controller).setBaseIssue(initiative);
//        controller.addIssue(epic);
    }


    public static void main(String[] args){
        launch(args);
    }
}
