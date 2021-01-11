package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.List;

/** <b>Initiative class</b>
 * Biggest class in issues hierarchy
 * This class describes the biggest abstraction layer called initiative.
 * This is a goal that can be reached by implementing at least one epic -
 * set of tasks related to some topic, which epic describes.
 */
public class Initiative extends IssueAbstract{
    /**
     * List of epics in initiative
     */
    List<Epic> epics;

    /** <b>Initiative Builder class</b>
     * This class is the builder that builds the Initiative object.
     */
    public static class InitiativeBuilder extends IssueBuilderAbstract{
        /**
         * List of included epics
         */
        List<Epic> fieldEpicsList = new ArrayList<>();

        /**
         * Epics list setter
         * @param epics Epics list
         * @return Current Builder object
         */
        public InitiativeBuilder epics(List<Epic> epics){
            fieldEpicsList = epics;
            return this;
        }

        /**
         * Method that adds epic to the list of included epics
         * @param epic Epic object to be added
         * @return Current Builder object
         */
        public InitiativeBuilder addEpic(Epic epic){
            fieldEpicsList.add(epic);
            return this;
        }

        /**
         * Method that builds Initiative object
         * based on parameters of builder
         * @return Initiative object
         */
        public Initiative build(){
            return new Initiative(this);
        }
    }

    /**
     * Default constructor
     */
    public Initiative(){
        super();
        epics = new ArrayList<>();
    }

    /**
     * Constructor based on name
     * @param name Name of Issue
     */
    public Initiative(String name){
        super(name);
        epics = new ArrayList<>();
    }

    /**
     * Constructor based on name and description
     * @param name Name of Issue
     * @param description Description of Issue
     */
    public Initiative(String name, String description){
        super(name, description);
        epics = new ArrayList<>();
    }

    /**
     * Constructor based on builder
     * @param builder Initiative Builder object
     */
    public Initiative(InitiativeBuilder builder){
        super(builder);
        epics = builder.fieldEpicsList;
    }

    //getters
    @Override
    public List<Epic> getSubIssues(){
        return epics;
    }

    //setters
    @Override
    public void setSubIssues(List<? extends IssueAbstract> epics){
        for(IssueAbstract epic: epics){
            addIssue(epic);
        }
    }

    @Override
    public void addIssue(IssueAbstract issue){
        if(issue instanceof Epic)
            epics.add((Epic) issue);
    }
}
