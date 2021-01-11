package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.EntityAbstract;
import fit.biepjv.agilediary.models.IssueAbstract;

import java.util.List;

/** <b>Base Controller class</b>
 * This is a abstract class that is the base for all controllers in the project.
 * Each controller controls the single corresponding model object.
 * Furthermore, all controllers will be used with UI controllers, which are in separated package.
 */
public abstract class BaseControllerAbstract {
    /**
     * Model instance
     */
    protected EntityAbstract entity;

    /** <b>Base Controller Builder class</b>
     * This is an abstract class that is base for all ControllerBuilder classes in the project.
     */
    public static abstract class BaseControllerBuilderAbstract{
        /**
         * Model builder instance
         */
        public EntityAbstract.EntityBuilderAbstract entityBuilder;

        /**
         * Method that build the controller based on current builder object
         * @return Controller object
         */
        public abstract BaseControllerAbstract build();
    }

    /**
     * Constructor based on builder
     * @param builder Controller Builder
     */
    public BaseControllerAbstract(BaseControllerBuilderAbstract builder){
        entity = builder.entityBuilder.build();
    }

    /**
     * Default constructor
     */
    public BaseControllerAbstract(){ }

    //getters

    /**
     * Name getter
     * @return Name of the model
     */
    public String getName(){
        return entity.getName();
    }

    /**
     * Description getter
     * @return Description of the object
     */
    public String getDescription(){
        return entity.getDescription();
    }

    /**
     * Method that returns list of included issues wrapped by controllers
     * @return List of all included issues controllers
     */
    public abstract List<? extends IssueControllerAbstract> getIncludedIssuesList();

    //setters

    /**
     * Entity Name setter
     * @param name Model name to be set
     */
    public void setName(String name){
        entity.setName(name);
    }

    /**
     * Entity Description setter
     * @param description Entity description
     */
    public void setDescription(String description){
        entity.setDescription(description);
    }

    /**
     * Method that sets the list of included issues
     * @param list List of included issues
     */
    public abstract void setIncludedIssuesList(List<? extends IssueAbstract> list);

    /**
     * Method that adds the issue to the list of included ones.
     * @param issue Issue to be added
     */
    protected abstract void addIssue(IssueAbstract issue);

    /**
     * Method that adds the issue with the controller
     * @param issueController Issue controller to be added
     */
    public abstract void addIssueController(IssueControllerAbstract issueController);
}
