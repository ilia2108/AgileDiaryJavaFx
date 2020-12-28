package fit.biepjv.agilediary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public ComboBox<String> comboBox_ThemesList;

    @FXML
    public DatePicker datePicker_DueDate;

    @FXML
    public Label txt_RelatedIssue;

    @FXML
    public ComboBox<String> comboBox_RelatedIssue;

}
