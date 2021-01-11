package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.List;

/** <b>Epic class</b>
 * This class describes sets of simple tasks that are combined with some common meaning.
 * In most of cases, the epics are created based on either technology or semantic preferences.
 */
public class Epic extends IssueAbstract{
    /**
     * List of stories in epic
     */
    List<Story> stories;

    /** <b>Builder of Epic class</b>
     * This class describes the builder of Epic object.
     */
    public static class EpicBuilder extends IssueBuilderAbstract{
        /**
         * List of included stories
         */
        List<Story> fieldStories = new ArrayList<>();

        /**
         * Method that builds the object based on builder
         * @return Epic object
         */
        public Epic build(){
            return new Epic(this);
        }

        /**
         *
         * @param story Story to be added
         * @return Current Builder object
         */
        public EpicBuilder addStory(Story story){
            fieldStories.add(story);
            return this;
        }

        /**
         * Method that sets list of stories
         * @param stories List of Stories
         * @return Current Builder object
         */
        public EpicBuilder stories(List<Story> stories){
            fieldStories = stories;
            return this;
        }
    }

    /**
     * Default constructor
     */
    public Epic(){
        super();
        stories = new ArrayList<>();
    }

    /**
     * Constructor based on name
     * @param name Epic name
     */
    public Epic(String name){
        super(name);
        stories = new ArrayList<>();
    }

    /**
     * Constructor based on name and description
     * @param name Epic name
     * @param description Epic description
     */
    public Epic(String name, String description){
        super(name, description);
        stories = new ArrayList<>();
    }

    /**
     * Constructor based on builder
     * @param builder Builder of Epic
     */
    public Epic(EpicBuilder builder){
        super(builder);
        this.name = builder.fieldName;
        this.description = builder.fieldDescription;
        this.stories = builder.fieldStories;
    }


    //getters

    @Override
    public List<Story> getSubIssues(){
        return stories;
    }

    //setters
    @Override
    public void setSubIssues(List<? extends IssueAbstract> stories){
        for (IssueAbstract story : stories) {
            addIssue(story);
        }
    }

    @Override
    public void addIssue(IssueAbstract issue){
        if(issue instanceof Story)
            stories.add((Story) issue);
    }
}
