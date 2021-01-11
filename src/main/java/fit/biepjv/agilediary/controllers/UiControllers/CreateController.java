package fit.biepjv.agilediary.controllers.UiControllers;

import com.sun.javaws.exceptions.InvalidArgumentException;
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

/** <b>Crate Page Controller</b>
 * This class stands for controlling the behaviour of Create page.
 * It creates all entities in the project.
 */
public class CreateController extends BaseUiControllerAbstract{
    /**
     * JavaFX Label representing page heading
     */
    @FXML
    public Label txt_Heading;

    /**
     * JavaFX Stack representing fields for issues only
     */
    @FXML
    public VBox vBox_IssuesForm;

    /**
     * JavaFX Button for adding the entity
     */
    @FXML
    public Button btn_Add;

    /**
     * JavaFX TextArea representing entity heading
     */
    @FXML
    public TextArea txt_Name;

    /**
     * JavaFX TextArea representing entity description
     */
    @FXML
    public TextArea txt_Description;

    /**
     * JavaFX TextArea representing assignees
     */
    @FXML
    public TextArea txt_Assignees;

    /**
     * JavaFX TextArea representing priority
     */
    @FXML
    public TextArea txt_Priority;

    /**
     * JavaFX ComboBox representing list of available themes
     */
    @FXML
    public ComboBox<String> comboBox_ThemesList;

    /**
     * JavaFX DatePicker to select deadline date
     */
    @FXML
    public DatePicker datePicker_DueDate;

    /**
     * JavaFX Label representing related issue name
     * (supports text for corresponding ComboBox)
     */
    @FXML
    public Label txt_RelatedIssue;

    /**
     * JavaFX ComboBox representing list of related issues
     */
    @FXML
    public ComboBox<String> comboBox_RelatedIssue;

    /**
     * Type of entity that needs to be created
     */
    String type;

    /**
     * Instance of MainController
     */
    MainController mainController;

    /**
     * Controller based on stage, db connector, entity type and mainController entity
     * @param stage Stage from Main page
     * @param connector Database connector
     * @param type Type of entity to create
     * @param mainController Instance of main controller
     */
    public CreateController(Stage stage, DatabaseConnector connector, String type, MainController mainController){
        super(stage, connector);
        this.type = type;
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // UI setup
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

        //setup all ui elements based on its type
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

        //button add click event
        btn_Add.setOnMouseClicked(addItem -> {
            BaseControllerAbstract.BaseControllerBuilderAbstract builder;
            Calendar calendar = Calendar.getInstance();
            LocalDate dueDate = datePicker_DueDate.getValue();
            //add an entity to db based on its type
            switch (type){
                case "theme":
                    builder = new ThemeController.ThemeControllerBuilder();
                    builder.entityBuilder
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());
                    try {
                        if(!dbConnector.themeExists(txt_Name.getText()))
                            dbConnector.addTheme((Theme) builder.entityBuilder.build());
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
                            .priority(Integer.parseInt(txt_Priority.getText()))
                            .dueDateString(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth())
                            .addAssignee(txt_Assignees.getText())
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());

                    mainController.initiativeControllers.add((InitiativeController)builder.build());
                    try {
                        if(!dbConnector.initiativeExists(txt_Name.getText()))
                        {
                            dbConnector.addInitiative((InitiativeController)
                                    ((InitiativeController.InitiativeControllerBuilder) builder)
                                            .build()
                            );
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case "epic":
                    builder = new EpicController.EpicControllerBuilder();
                    calendar.set(dueDate.getYear(), dueDate.getMonthValue()-1, dueDate.getDayOfMonth());
                    ((EpicController.EpicControllerBuilder) builder)
                            .addThemeController(
                                    mainController.findThemeControllerByName(comboBox_ThemesList.getValue())
                            )
                            .issueBuilder
                            .priority(Integer.parseInt(txt_Priority.getText()))
                            .dueDateString(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth())
                            .addAssignee(txt_Assignees.getText())
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());

                    for(InitiativeController initiative: mainController.initiativeControllers){
                        if(initiative.getName().equals(comboBox_RelatedIssue.getValue())) {
                            try {
                                EpicController epicController = (EpicController) builder.build();
                                dbConnector.addEpic(epicController);
                                dbConnector.addRelation(initiative, epicController);
                            } catch (SQLException | InvalidArgumentException e) {
                                e.printStackTrace();
                            }
                        }
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
                            .priority(Integer.parseInt(txt_Priority.getText()))
                            .dueDateString(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth())
                            .addAssignee(txt_Assignees.getText())
                            .name(txt_Name.getText())
                            .description(txt_Description.getText());
                    for(InitiativeController initiative: mainController.initiativeControllers){
                        for(EpicController epic: initiative.getIncludedIssuesList()){
                            if(epic.getName().equals(comboBox_RelatedIssue.getValue()))
                                try {
                                    StoryController storyController = (StoryController) builder.build();
                                    dbConnector.addStory(storyController);
                                    dbConnector.addRelation(epic, storyController);
                                } catch (SQLException | InvalidArgumentException e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                    break;
            }

            //loading the main page
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
