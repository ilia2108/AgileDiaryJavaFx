package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends IssueAbstract{
    List<Story> stories;

    public Epic(){
        super();
        stories = new ArrayList<>();
    }

    public List<Story> getStories(){
        return stories;
    }

    public void setStories(List<Story> stories){
        setUpdatedAt();
        this.stories = stories;
    }
    public void addStory(Story story){
        setUpdatedAt();
        stories.add(story);
    }
}
