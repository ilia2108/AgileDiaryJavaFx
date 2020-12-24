package fit.biepjv.agilediary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/*
    \class Main Controller class
    \brief Default "facade" between View and Model

    This is a basic controller class. It contains all other controllers and supports displaying of View.
 */
public class MainController {

    public Map<EntityTypes, BaseControllerAbstract> controllersList = initControllers();

    public enum EntityTypes{
        THEME, INITIATIVE, EPIC, STORY
    }

    private Map<EntityTypes, BaseControllerAbstract> initControllers(){
        Map<EntityTypes, BaseControllerAbstract> result = new HashMap<>();

        //todo: fill for all from EntityTypes
        result.put(EntityTypes.THEME, new ThemeController());
        result.put(EntityTypes.INITIATIVE, new InitiativeController());
        result.put(EntityTypes.EPIC, new EpicController());
        result.put(EntityTypes.STORY, new StoryController());

        return result;
    }

    @FXML
    public Button btn_Add;

    @FXML
    public Label txt_Heading;

    @FXML
    public GridPane grid_noContent;

    @FXML
    public Label txt_Intro;

    @FXML
    public Accordion accordion_Items;

    @FXML
    public Button btn_AddDefault;

    @FXML
    public Button btn_Back;
}
