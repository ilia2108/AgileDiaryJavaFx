package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.List;

/*
    \class Epic class

    This class describes sets of simple tasks that are combined with some common meaning.
    In most of cases, the epics are created based on either technology or semantic preferences.
 */
public class Epic extends IssueAbstract{
    List<Story> stories; /// list of stories in epic

    public Epic(){
        super();
        stories = new ArrayList<>();
    }
    public Epic(String name){
        super(name);
        stories = new ArrayList<>();
    }
    public Epic(String name, String description){
        super(name, description);
        stories = new ArrayList<>();
    }

    //getters
    public List<Story> getStories(){
        return stories;
    }

    //setters
    public void setStories(List<Story> stories){
        setUpdatedAt();
        this.stories = stories;
    }
    public void addStory(Story story){
        setUpdatedAt();
        stories.add(story);
    }
}
