package fit.biepjv.agilediary.events.handlers;

import fit.biepjv.agilediary.Main;
import fit.biepjv.agilediary.controllers.UiControllers.CreateController;
import fit.biepjv.agilediary.controllers.UiControllers.MainController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**<b>Event handler for clicking the <i>"Add"</i> button</b>
 * This class is used to describe the behaviour when the adding event is executed.
 */
public abstract class AddIssueEventHandler implements EventHandler<Event> {
    /**
     * Type of entity to add
     */
    String type;
    /**
     * Instance of Main Controller
     */
    MainController mainController;

    /**
     * Base stage to change
     */
    Stage stage;

    /**
     * Constructor based on entity type, stage and controller instance
     * @param type Type of entity
     * @param stage Default stage
     * @param controller Instance of main controller
     */
    public AddIssueEventHandler(String type,
                                Stage stage,
                               MainController controller){
        this.type = type;
        this.mainController = controller;
        this.stage = stage;
    }

    /**
     * Entity type getter
     * @return
     */
    public String getType(){
        return type;
    }

    /**
     * Function that is executed when the event is triggered
     * @param event Event that was triggered
     */
    public void handle(Event event){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/CreatePage.fxml"));
        Parent root = null;
        CreateController controller =
                new CreateController(stage, mainController.getDbConnector(), type, mainController);
        loader.setController(controller);
        try {
            root = loader.load();
        }
        catch (Exception ignored){
        }
        Scene scene = new Scene(root, 600,400);

        stage.setScene(scene);
        stage.show();
    }
}
