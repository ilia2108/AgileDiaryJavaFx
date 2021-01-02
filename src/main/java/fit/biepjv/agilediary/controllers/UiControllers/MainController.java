package fit.biepjv.agilediary.controllers.UiControllers;

import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.events.handlers.AddIssueEventHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    \class Main Controller class
    \brief Default "facade" between View and Model

    This is a basic controller class. It contains all other controllers and supports displaying of View.
 */
public class MainController extends BaseUiControllerAbstract{

    public static Map<String, BaseControllerAbstract> controllersList = initControllers();

    public List<ThemeController> themeControllers = new ArrayList<>();
    public List<InitiativeController> initiativeControllers = new ArrayList<>();
    //Stage stage = new Stage();

    public MainController(Stage stage){
        super(stage);
    }

    private static Map<String, BaseControllerAbstract> initControllers(){
        Map<String, BaseControllerAbstract> result = new HashMap<>();

        result.put("theme", new ThemeController());
        result.put("initiative", new InitiativeController());
        result.put("epic", new EpicController());
        result.put("story", new StoryController());

        return result;
    }

    @FXML
    public Button btn_AddTheme;

    @FXML
    public Button btn_AddInitiative;

    @FXML
    public Button btn_AddEpic;

    @FXML
    public Button btn_AddStory;

    @FXML
    public GridPane grid_noContent;
    @FXML
    public GridPane grid_Issues;
    @FXML
    public GridPane grid_AddButtons;
    @FXML
    public GridPane grid_themes;
    @FXML
    public Label txt_Intro;
    @FXML
    public Accordion accordion_Items;
    @FXML
    public Button btn_AddDefault;
    @FXML
    public Button btn_Back;
    @FXML
    public VBox vBox_Themes;

    @FXML
    public void initialize(){

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

    public ThemeController findThemeControllerByName(String name){
        for(ThemeController controller: themeControllers){
            if(controller.getName().equals(name))
                return controller;
        }
        return null;
    }
}
