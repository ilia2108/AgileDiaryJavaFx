package fit.biepjv.agilediary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
    \class Main Controller class
    \brief Default "facade" between View and Model

    This is a basic controller class. It contains all other controllers and supports displaying of View.
 */
public class MainController {

    public Map<String, BaseControllerAbstract> controllersList = initControllers();

    private Map<String, BaseControllerAbstract> initControllers(){
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
    public Label txt_Heading;
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

}
