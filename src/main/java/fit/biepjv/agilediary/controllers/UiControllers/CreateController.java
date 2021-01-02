package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.Main;
import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.jdbc.DatabaseConnector;
import fit.biepjv.agilediary.models.Theme;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

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

    public CreateController(Stage stage, DatabaseConnector connector, String type, MainController mainController){
        super(stage, connector);
        this.type = type;
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        stage.setTitle("Add " + type);
        txt_Heading.setText("Add a " + type);
        vBox_IssuesForm.setVisible(!type.equals("theme"));
        for(ThemeController themeController: mainController.themeControllers){
            comboBox_ThemesList.getItems().add(themeController.getName());
        }
        comboBox_ThemesList.setValue(
                comboBox_ThemesList.getItems().size() ==0 ?
                        "There're no themes":
                        comboBox_ThemesList.getItems().get(0)
        );
        String relatedIssueText = "Related ";
        switch (type){
            case "epic":
                relatedIssueText += "Initiative";
                for(InitiativeController initiativeController: mainController.initiativeControllers){
                    comboBox_RelatedIssue.getItems().add(initiativeController.getName());
                }
                comboBox_RelatedIssue.setValue(
                        comboBox_RelatedIssue.getItems().size() ==0 ?
                                "There're no initiatives":
                                comboBox_RelatedIssue.getItems().get(0)
                );
                break;
            case "story":
                relatedIssueText += "Epic";
                for(InitiativeController initiativeController: mainController.initiativeControllers){
                    for(EpicController epicController: initiativeController.getIncludedIssuesList()){
                        comboBox_RelatedIssue.getItems().add(epicController.getName());
                    }
                }
                comboBox_RelatedIssue.setValue(
                        comboBox_RelatedIssue.getItems().size() ==0 ?
                                "There're no epics":
                                comboBox_RelatedIssue.getItems().get(0)
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
                    //mainController.themeControllers.add((ThemeController)builder.build());
                    try {
                        if(!dbConnector.getThemeDAO().themeExists(txt_Name.getText()))
                            dbConnector.getThemeDAO().addTheme((Theme) builder.entityBuilder.build());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case "initiative":
                    builder = new InitiativeController.InitiativeControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((InitiativeController.InitiativeControllerBuilder) builder)
                            .addThemeController(
                                    mainController.findThemeControllerByName(comboBox_ThemesList.getValue())
                            )
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
                            .addThemeController(
                                    mainController.findThemeControllerByName(comboBox_ThemesList.getValue())
                            )
                            .issueBuilder
                            .dueDate(calendar)
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());

                    for(InitiativeController initiative: mainController.initiativeControllers){
                        if(initiative.getName().equals(comboBox_RelatedIssue.getValue()))
                            initiative.addIssueController((EpicController)builder.build());
                    }
                    break;
                case "story":
                    builder = new StoryController.StoryControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((StoryController.StoryControllerBuilder) builder)
                            .addThemeController(
                                    mainController.findThemeControllerByName(comboBox_ThemesList.getValue())
                            )
                            .issueBuilder
                            .dueDate(calendar)
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());
                    for(InitiativeController initiative: mainController.initiativeControllers){
                        for(EpicController epic: initiative.getIncludedIssuesList()){
                            if(epic.getName().equals(comboBox_RelatedIssue.getValue()))
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
