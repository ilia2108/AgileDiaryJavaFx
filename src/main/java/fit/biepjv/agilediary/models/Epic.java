package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.List;

/*
    \class Epic class

    This class describes sets of simple tasks that are combined with some common meaning.
    In most of cases, the epics are created based on either technology or semantic preferences.
 */
public class Epic extends IssueAbstract{
    List<Story> stories; /// list of stories in epic

    public static class EpicBuilder extends IssueBuilderAbstract{
        List<Story> fieldStories = new ArrayList<>();

        public Epic build(){
            return new Epic(this);
        }

        public void addStory(Story story){
            fieldStories.add(story);
        }
        public void stories(List<Story> stories){
            fieldStories = stories;
        }
    }

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
    public Epic(EpicBuilder builder){
        super(builder);
        this.name = builder.fieldName;
        this.description = builder.fieldDescription;
        this.stories = builder.fieldStories;
    }


    //getters
    public List<Story> getSubIssues(){
        return stories;
    }

    //setters
    public void setSubIssues(List<? extends IssueAbstract> stories){
        setUpdatedAt();
        for (IssueAbstract story : stories) {
            addIssue(story);
        }
    }
    public void addIssue(IssueAbstract issue){
        setUpdatedAt();
        if(issue instanceof Story)
            stories.add((Story) issue);
    }
}
