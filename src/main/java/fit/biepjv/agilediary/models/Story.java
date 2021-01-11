package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.List;


/** <b>Story class</b>

    This is the smallest implementation of issue. It stands for a single task (or story) which can be used
    further to build something bigger. It can contain substories, however, there are not implemented yet.
 */
public class Story extends IssueAbstract{
    /**
     *  List of included smaller stories (might be empty)
     */
    List<Story> substories;

    /** <b>Story Builder class</b>
     * This is the builder of Story object.
     */
    public static class StoryBuilder extends IssueBuilderAbstract{
        /**
         * List of included stories aka substories
         */
        List<Story> fieldSubstories = new ArrayList<>();

        /**
         * Method that sets list of stories that are included
         * @param stories List of stories
         * @return Current Builder object
         */
        public StoryBuilder substories(List<Story> stories){
            fieldSubstories = stories;
            return this;
        }

        /**
         * Method that adds story to the list of included stories
         * @param story Story to be added
         * @return Current Builder object
         */
        public StoryBuilder addSubstory(Story story){
            fieldSubstories.add(story);
            return this;
        }

        public Story build(){
            return new Story(this);
        }
    }

    /**
     * Default constructor
     */
    public Story(){
        super();
        substories = new ArrayList<>();
    }

    /**
     * Constructor based on name
     * @param name Story name
     */
    public Story(String name){
        super(name);
        substories = new ArrayList<>();
    }

    /**
     * Constructor based on name and description
     * @param name Story name
     * @param description Story description
     */
    public Story(String name, String description){
        super(name, description);
        substories = new ArrayList<>();
    }

    /**
     * Constructor based on builder
     * @param builder Story builder object
     */
    public Story(StoryBuilder builder){
        super(builder);
        substories = builder.fieldSubstories;
    }

    //getters
    @Override
    public List<Story> getSubIssues() {
        return substories;
    }

    //setters
    @Override
    public void setSubIssues(List<? extends IssueAbstract> substories){
        for (IssueAbstract substory : substories) {
            addIssue(substory);
        }
    }

    @Override
    public void addIssue(IssueAbstract issue){
        if(issue instanceof Story)
            substories.add((Story) issue);
    }
}
