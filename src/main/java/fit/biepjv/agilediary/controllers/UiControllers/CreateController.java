package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.Main;
import fit.biepjv.agilediary.controllers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateController extends BaseUiControllerAbstract{
    @FXML
    public Label txt_Heading;

    @FXML
    public VBox vBox_IssuesForm;

    @FXML
    public Button btn_Add;

    @FXML
    public TextArea txt_Name;

    @FXML
    public TextArea txt_Description;

    @FXML
    public TextArea txt_Assignees;

    @FXML
    public TextArea txt_Priority;

    @FXML
    public ComboBox<String> comboBox_ThemesList;

    @FXML
    public DatePicker datePicker_DueDate;

    @FXML
    public Label txt_RelatedIssue;

    @FXML
    public ComboBox<String> comboBox_RelatedIssue;

    String type;
    MainController mainController;

    public CreateController(Stage stage, String type, MainController mainController){
        super(stage);
        this.type = type;
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        stage.setTitle("Add " + type);
        txt_Heading.setText("Add a " + type);
        vBox_IssuesForm.setVisible(!type.equals("theme"));
        List<String> themesStringBase = new ArrayList<>();
        for(ThemeController themeController: mainController.themeControllers){
            themesStringBase.add(themeController.getName());
        }
        ObservableList<String> themesStrings = FXCollections.observableArrayList(themesStringBase);
        comboBox_ThemesList = new ComboBox<String>(themesStrings);
        comboBox_ThemesList.setValue(
                themesStrings.size() ==0 ? "There're no themes": themesStrings.get(0)
        );
        String relatedIssueText = "Related ";
        switch (type){
            case "epic":
                relatedIssueText += "Initiative";
                List<String> initiativesListBase = new ArrayList<>();
                for(InitiativeController initiativeController: mainController.initiativeControllers){
                    initiativesListBase.add(initiativeController.getName());
                }
                ObservableList<String> initiativesStrings =
                        FXCollections.observableArrayList(initiativesListBase);
                comboBox_RelatedIssue = new ComboBox<>(initiativesStrings);
                comboBox_RelatedIssue.setValue(
                        initiativesStrings.size() ==0 ? "There're no initiatives": themesStrings.get(0)
                );
                break;
            case "story":
                relatedIssueText += "Epic";
                List<String> epicsListBase = new ArrayList<>();
                for(InitiativeController initiativeController: mainController.initiativeControllers){
                    for(EpicController epicController: initiativeController.getIncludedIssuesList()){
                        epicsListBase.add(epicController.getName());
                    }
                }
                ObservableList<String> epicsStrings =
                        FXCollections.observableArrayList(epicsListBase);
                comboBox_RelatedIssue = new ComboBox<>(epicsStrings);
                comboBox_RelatedIssue.setValue(
                        epicsStrings.size() ==0 ? "There're no epics": themesStrings.get(0)
                );
                break;
            default:
                txt_RelatedIssue.setVisible(false);
                comboBox_RelatedIssue.setVisible(false);
                break;
        }
        txt_RelatedIssue.setText(relatedIssueText);

        btn_Add.setOnMouseClicked(addItem -> {
            BaseControllerAbstract.BaseControllerBuilderAbstract builder;
            Calendar calendar = Calendar.getInstance();
            LocalDate dueDate = datePicker_DueDate.getValue();
            switch (type){
                case "theme":
                    builder = new ThemeController.ThemeControllerBuilder();
                    builder.entityBuilder
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());
                    mainController.themeControllers.add((ThemeController)builder.build());
                    break;
                case "initiative":
                    builder = new InitiativeController.InitiativeControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((InitiativeController.InitiativeControllerBuilder) builder)
                            .issueBuilder
                            .priority(1)
                            .dueDate(calendar)
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());

                    mainController.initiativeControllers.add((InitiativeController)builder.build());
                    break;
                case "epic":
                    builder = new EpicController.EpicControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((EpicController.EpicControllerBuilder) builder)
                            .issueBuilder
                            .dueDate(calendar)
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());

                    for(InitiativeController initiative: mainController.initiativeControllers){
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
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());
                    for(InitiativeController initiative: mainController.initiativeControllers){
                        for(EpicController epic: initiative.getIncludedIssuesList()){
                            //todo: find corresponding epic
                            epic.addIssueController((StoryController)builder.build());
                        }
                    }
                    break;
            }
            FXMLLoader oldLoader = new FXMLLoader(Main.class.getResource("views/MainPage.fxml"));
            oldLoader.setController(mainController);
            Parent oldRoot = null;
            try {
                oldRoot = oldLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene oldScene = new Scene(oldRoot, 600,400);


            stage.setScene(oldScene);
            stage.show();
        });
    }
}
