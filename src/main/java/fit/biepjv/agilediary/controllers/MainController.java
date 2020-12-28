package fit.biepjv.agilediary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    \class Main Controller class
    \brief Default "facade" between View and Model

    This is a basic controller class. It contains all other controllers and supports displaying of View.
 */
public class MainController {

    public static Map<String, BaseControllerAbstract> controllersList = initControllers();

    public List<ThemeController> themeControllers = new ArrayList<>();
    public List<InitiativeController> initiativeControllers = new ArrayList<>();

    private static Map<String, BaseControllerAbstract> initControllers(){
        Map<String, BaseControllerAbstract> result = new HashMap<>();

        result.put("theme", new ThemeController());
        result.put("initiative", new InitiativeController());
        result.put("epic", new EpicController());
        result.put("story", new StoryController());

        return result;
    }

    @FXML
    public Button btn_Add;
    @FXML
    public Button btn_AddTheme;

    @FXML
    public GridPane grid_noContent;
    @FXML
    public GridPane grid_Issues;
    @FXML
    public GridPane grid_NavigationButtons;
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
    public HBox hBox_Themes;

    @FXML
    public void click_GoBack(ActionEvent e){
        //todo: implement
    }

    @FXML
    public void initialize(){
        boolean noThemes = themeControllers.size() == 0;
        boolean noInitiatives = initiativeControllers.size() == 0;
        grid_Issues.setVisible(!noInitiatives);
        grid_themes.setVisible(!noThemes);
        grid_noContent.setVisible(noInitiatives && noThemes);

        for(InitiativeController initiativeController: initiativeControllers){
            TitledPane epicPane = null;
            for(EpicController epicController: initiativeController.getIncludedIssuesList()){
                VBox vbox_stories = new VBox(15);
                for(StoryController story: epicController.getIncludedIssuesList()){
                    vbox_stories.getChildren().add(new Label(story.getName()));
                }
                epicPane = new TitledPane(epicController.getName(), vbox_stories);
            }
            TitledPane initiativePane = new TitledPane(initiativeController.getName(), epicPane);
            accordion_Items.getPanes().add(initiativePane);
        }

        for (ThemeController themeController: themeControllers){
            hBox_Themes.getChildren().add(new Label(themeController.getName()));
        }

    }
}
