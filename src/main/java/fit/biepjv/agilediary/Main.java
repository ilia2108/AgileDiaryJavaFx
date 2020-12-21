package fit.biepjv.agilediary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends javafx.application.Application {

    public void start(Stage stage) {

        Button nextButton = new Button("Next");
        EventHandler<ActionEvent> actionHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //semaphorControler.next();
            }
        };
        nextButton.setOnAction(actionHandler);
        HBox buttonBar = new HBox(nextButton);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setSpacing(10);
        VBox root = new VBox(buttonBar);
        Scene s = new Scene(root);
        stage.setScene(s);
        stage.show();

    }
}
