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
import java.util.Calendar;
import java.time.LocalDate;
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
        String relatedIssueText = "Related ";
        switch (type){
            case "epic":
                relatedIssueText += "Initiative";
                List<String> initiativesListBase = new ArrayList<>();
                for(InitiativeController initiativeController: initiativeControllers){
                    initiativesListBase.add(initiativeController.getName());
                }
                ObservableList<String> initiativesStrings =
                        FXCollections.observableArrayList(initiativesListBase);
                controller.comboBox_RelatedIssue = new ComboBox<>(initiativesStrings);
                controller.comboBox_RelatedIssue.setValue(
                        initiativesStrings.size() ==0 ? "There're no initiatives": themesStrings.get(0)
                );
                break;
            case "story":
                relatedIssueText += "Epic";
                List<String> epicsListBase = new ArrayList<>();
                for(InitiativeController initiativeController: initiativeControllers){
                    for(EpicController epicController: initiativeController.getIncludedIssuesList()){
                        epicsListBase.add(epicController.getName());
                    }
                }
                ObservableList<String> epicsStrings =
                        FXCollections.observableArrayList(epicsListBase);
                controller.comboBox_RelatedIssue = new ComboBox<>(epicsStrings);
                controller.comboBox_RelatedIssue.setValue(
                        epicsStrings.size() ==0 ? "There're no epics": themesStrings.get(0)
                );
                break;
            default:
                controller.txt_RelatedIssue.setVisible(false);
                controller.comboBox_RelatedIssue.setVisible(false);
                break;
        }
        controller.txt_RelatedIssue.setText(relatedIssueText);

        controller.btn_Add.setOnMouseClicked(addItem -> {
            BaseControllerAbstract.BaseControllerBuilderAbstract builder;
            Calendar calendar = Calendar.getInstance();
            LocalDate dueDate = controller.datePicker_DueDate.getValue();
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
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((InitiativeController.InitiativeControllerBuilder) builder)
                            .issueBuilder
                            .priority(1)
                            .dueDate(calendar)
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());

                    initiativeControllers.add((InitiativeController)builder.build());
                    break;
                case "epic":
                    builder = new EpicController.EpicControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((EpicController.EpicControllerBuilder) builder)
                            .issueBuilder
                            .dueDate(calendar)
                            .name(controller.txt_Name.getText())
                            .description(controller.txt_Description.getText());

                    for(InitiativeController initiative: initiativeControllers){
                        //todo: find corresponding initiative
                        initiative.addIssueController((EpicController)builder.build());
                    }
                    break;
                case "story":
                    builder = new StoryController.StoryControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((StoryController.StoryControllerBuilder)builder)
                            .issueBuilder
                            .dueDate(calendar)
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
