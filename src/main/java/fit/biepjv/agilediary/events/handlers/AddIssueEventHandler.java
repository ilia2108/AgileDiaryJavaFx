package fit.biepjv.agilediary.events.handlers;

import javafx.event.Event;
import javafx.event.EventHandler;

public abstract class AddIssueEventHandler implements EventHandler<Event> {
    String type;
    public AddIssueEventHandler(String type){
        this.type = type;

    }
    public String getType(){
        return type;
    }
}
