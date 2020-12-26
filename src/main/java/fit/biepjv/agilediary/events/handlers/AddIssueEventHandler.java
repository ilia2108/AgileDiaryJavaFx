package fit.biepjv.agilediary.events.handlers;

import fit.biepjv.agilediary.Main;
import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.models.EntityAbstract;
import fit.biepjv.agilediary.models.Initiative;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
        List<String> themesStringBase = new ArrayList<>();
        for(ThemeController themeController: themeControllers){
            themesStringBase.add(themeController.getName());
        }
        ObservableList<String> themesStrings = FXCollections.observableArrayList(themesStringBase);
        controller.comboBox_ThemesList = new ComboBox<String>(themesStrings);
        controller.comboBox_ThemesList.setValue(
                themesStrings.size() ==0 ? "There're no themes": themesStrings.get(0)
                );

        controller.btn_Add.setOnMouseClicked(addItem -> {
            BaseControllerAbstract.BaseControllerBuilderAbstract builder;
            switch (type){
                case "theme":
                    builder = new ThemeController.ThemeControllerBuilder();
                    builder.entityBuilder
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());
                    themeControllers.add((ThemeController)builder.build());
                   break;
                case "initiative":
                    builder = new InitiativeController.InitiativeControllerBuilder();
                    ((InitiativeController.InitiativeControllerBuilder) builder)
                            .issueBuilder
                            .priority(1)
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());

                    initiativeControllers.add((InitiativeController)builder.build());
                    break;
                case "epic":
                    builder = new EpicController.EpicControllerBuilder();
                    ((EpicController.EpicControllerBuilder) builder)
                            .issueBuilder
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());

                    for(InitiativeController initiative: initiativeControllers){
                        //todo: find corresponding initiative
                        initiative.addIssueController((EpicController)builder.build());
                    }
                    break;
                case "story":
                    builder = new StoryController.StoryControllerBuilder();
                    ((StoryController.StoryControllerBuilder)builder)
                            .issueBuilder
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());
                    for(InitiativeController initiative: initiativeControllers){
                        for(EpicController epic: initiative.getIncludedIssuesList()){
                            //todo: find corresponding epic
                            epic.addIssueController((StoryController)builder.build());
                        }
                    }
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
