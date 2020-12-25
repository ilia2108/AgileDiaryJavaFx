package fit.biepjv.agilediary.events.handlers;

import fit.biepjv.agilediary.Main;
import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.models.EntityAbstract;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class AddIssueEventHandler implements EventHandler<Event> {
    String type;
    Stage stage;
    List<ThemeController> themeControllers = new ArrayList<>();
    List<InitiativeController> initiativeControllers = new ArrayList<>();
    public AddIssueEventHandler(String type,
                                Stage stage,
                                List<ThemeController> themeControllers,
                                List<InitiativeController> initiativeControllers){
        this.type = type;
        this.stage = stage;
        this.themeControllers = themeControllers;
        this.initiativeControllers = initiativeControllers;
    }
    public String getType(){
        return type;
    }

    @Override
    public void handle(Event event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/CreatePage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }
        catch (Exception ignored){ }
        CreateController controller = loader.getController();
        assert root != null;
        Scene scene = new Scene(root, 600,400);
        stage.setTitle("Add " + type);
        controller.txt_Heading.setText("Add a " + type);
        controller.vBox_IssuesForm.setVisible(!type.equals("theme"));
        controller.btn_Add.setOnMouseClicked(addItem -> {

            switch (type){
                //IssueControllerAbstract issueController = null;
                case "theme":
                    ThemeController.ThemeControllerBuilder builder =
                            new ThemeController.ThemeControllerBuilder();
                    builder.entityBuilder
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());
                    themeControllers.add((ThemeController)builder.build());
                   break;
                case "initiative":
                    break;
                case "epic":
                    break;
                case "story":
                    break;

            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
