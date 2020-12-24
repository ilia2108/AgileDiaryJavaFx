package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.List;


/* \class
    \brief

    This is the smallest implementation of issue. It stands for a single task (or story) which can be used
    further to build something bigger
 */
public class Story extends IssueAbstract{
    List<Story> substories; /// list of included smaller stories (might be empty)

    public static class StoryBuilder extends IssueBuilderAbstract{
        List<Story> fieldSubstories = new ArrayList<>();

        public StoryBuilder substories(List<Story> stories){
            fieldSubstories = stories;
            return this;
        }
        public StoryBuilder addSubstory(Story story){
            fieldSubstories.add(story);
            return this;
        }

        public Story build(){
            return new Story(this);
        }
    }

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
    public Story(StoryBuilder builder){
        super(builder);
        substories = builder.fieldSubstories;
    }

    //getters
    public List<Story> getSubIssues() {
        return substories;
    }

    //setters
    public void setSubIssues(List<? extends IssueAbstract> substories){
        setUpdatedAt();
        for (IssueAbstract substory : substories) {
            addIssue(substory);
        }
    }
    public void addIssue(IssueAbstract issue){
        setUpdatedAt();
        if(issue instanceof Story)
            substories.add((Story) issue);
    }
}
