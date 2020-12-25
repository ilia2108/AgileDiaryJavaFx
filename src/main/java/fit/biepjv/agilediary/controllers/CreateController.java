package fit.biepjv.agilediary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import javax.xml.soap.Text;

public class CreateController {
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
    public TextArea txt_Themes;

    @FXML
    public TextArea txt_DueDate;
}
