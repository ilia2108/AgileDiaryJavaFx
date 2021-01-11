package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.events.handlers.AddIssueEventHandler;
import fit.biepjv.agilediary.jdbc.DatabaseConnector;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** <b>Main Controller class</b>
 * This is a controller class of first (main) page. It contains all other controllers and supports displaying of View.
 */
public class MainController extends BaseUiControllerAbstract{
    /**
     * List of Theme controllers
     */
    public List<ThemeController> themeControllers = new ArrayList<>();

    /**
     * List of Initiative controllers
     */
    public List<InitiativeController> initiativeControllers = new ArrayList<>();

    /**
     * Constructor based on stage and connector
     * @param stage JavaFX Stage
     * @param connector Database connector object
     */
    public MainController(Stage stage, DatabaseConnector connector){
        super(stage, connector);
    }

    /**
     * Database connector setter
     * @return Database connector
     */
    public DatabaseConnector getDbConnector(){
        return dbConnector;
    }

    /**
     * JavaFX Button to add theme
     */
    @FXML
    public Button btn_AddTheme;

    /**
     * JavaFX Button to add initiative
     */
    @FXML
    public Button btn_AddInitiative;

    /**
     * JavaFX Button to add epic
     */
    @FXML
    public Button btn_AddEpic;

    /**
     * JavaFX Button to add story
     */
    @FXML
    public Button btn_AddStory;

    /**
     * JavaFX Grid displaying default view
     */
    @FXML
    public GridPane grid_noContent;

    /**
     * JavaFX Grid displaying issues list
     */
    @FXML
    public GridPane grid_Issues;

    /**
     * JavaFX Grid with adding buttons
     */
    @FXML
    public GridPane grid_AddButtons;

    /**
     * JavaFX Grid describing themes
     */
    @FXML
    public GridPane grid_themes;

    /**
     * JavaFX Accordion describing issues
     */
    @FXML
    public Accordion accordion_Items;

    /**
     * JavaFX Button that adds first theme in default view
     */
    @FXML
    public Button btn_AddDefault;

    /**
     * JavaFX Stack of themes
     */
    @FXML
    public VBox vBox_Themes;


    @FXML
    public void initialize(){

        try {
            themeControllers = dbConnector.getThemeControllers();
            initiativeControllers = dbConnector.getInitiativeControllers();
        }
        catch (SQLException ignored){

        }

        boolean noThemes = themeControllers.size() == 0;
        boolean noInitiatives = initiativeControllers.size() == 0;
        grid_Issues.setVisible(!noInitiatives);
        grid_themes.setVisible(!noThemes);
        grid_AddButtons.setVisible(!noThemes);
        grid_noContent.setVisible(noThemes);

        for(InitiativeController initiativeController: initiativeControllers){
            TitledPane epicPane = null;
            VBox vbox_epics = new VBox(8);
            for(EpicController epicController: initiativeController.getIncludedIssuesList()){
                VBox vbox_stories = new VBox(8);
                for(StoryController story: epicController.getIncludedIssuesList()){
                    vbox_stories.getChildren().add(new Label(story.getName()));
                }
                epicPane = new TitledPane(epicController.getName(), vbox_stories);
                vbox_epics.getChildren().add(epicPane);
            }
            TitledPane initiativePane = new TitledPane(initiativeController.getName(), vbox_epics);
            accordion_Items.getPanes().add(initiativePane);
        }

        for (ThemeController themeController: themeControllers){
            vBox_Themes.getChildren().add(new Label(themeController.getName()));
        }

        AddIssueEventHandler themeAdd =
                new AddIssueEventHandler("theme", stage, this) {
                    @Override
                    public void handle(Event event) {
                        super.handle(event);
                    }
                };

        btn_AddTheme.setOnMouseClicked(themeAdd);
        btn_AddInitiative.setOnMouseClicked(new AddIssueEventHandler(
                "initiative", stage, this) {
            @Override
            public void handle(Event event) {
                super.handle(event);
            }
        });
        btn_AddEpic.setOnMouseClicked(new AddIssueEventHandler(
                "epic", stage, this) {
            @Override
            public void handle(Event event) {
                super.handle(event);
            }
        });
        btn_AddStory.setOnMouseClicked(new AddIssueEventHandler(
                "story", stage, this) {
            @Override
            public void handle(Event event) {
                super.handle(event);
            }
        });

    }

    /**
     * Method that searches for ThemeController with specific name
     * @param name Name of controller to search
     * @return Theme Controller with the name
     */
    public ThemeController findThemeControllerByName(String name){
        for(ThemeController controller: themeControllers){
            if(controller.getName().equals(name))
                return controller;
        }
        return null;
    }
}
