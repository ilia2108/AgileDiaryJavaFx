package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.List;


/* \class
    \brief

    This is the smallest implementation of issue. It stands for a single task (or story) which can be used
    further to build something bigger
 */
public class Story extends IssueAbstract{
    List<Story> substories; /// list of included smaller stories (might be empty)

    public Story(){
        super();
        substories = new ArrayList<>();
    }
    public Story(String name){
        super(name);
        substories = new ArrayList<>();
    }
    public Story(String name, String description){
        super(name, description);
        substories = new ArrayList<>();
    }

    //getters
    public List<Story> getSubstories() {
        return substories;
    }

    //setters
    public void setSubstories(List<Story> substories) {
        this.substories = substories;
    }
    public void addSubstory(Story substory){
        substories.add(substory);
    }
}
