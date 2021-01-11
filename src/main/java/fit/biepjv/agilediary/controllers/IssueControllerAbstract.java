package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;

import java.util.Calendar;
import java.util.List;

/** <b>Issue Controller class</b>
 * This class stands for Controller of any issue.
 * Each IssueController controls the issue as in the model hierarchy.
 */
public abstract class IssueControllerAbstract extends BaseControllerAbstract{
    /**
     * Issue instance
     */
    protected IssueAbstract issue = null;

    /** <b>Issue Controller Builder class</b>
     * This class is used to build a IssueController.
     */
    public static abstract class IssueControllerBuilderAbstract extends BaseControllerBuilderAbstract{
        /**
         * Issue Builder instance
         */
        public IssueAbstract.IssueBuilderAbstract issueBuilder;

        @Override
        public abstract IssueControllerAbstract build();

        /**
         * Method that adds theme to the issue based on controller
         * @param themeController Theme Controller to add
         * @return Current Builder object
         */
        public IssueControllerBuilderAbstract addThemeController(ThemeController themeController){
            issueBuilder.addTheme((Theme) themeController.entity);
            return this;
        }
    }

    /**
     * Default constructor
     */
    public IssueControllerAbstract(){}

    /**
     * Constructor based on controller builder
     * @param builder Controller builder
     */
    public IssueControllerAbstract(IssueControllerBuilderAbstract builder){
       issue = (IssueAbstract) builder.issueBuilder.build();
    }

    /**
     * Constructor based on model instance
     * @param issue Model instance
     */
    public IssueControllerAbstract(IssueAbstract issue){
        this.issue = issue;
    }

    //getters

    /**
     * Deadline getter
     * @return Issue deadline
     */
    public Calendar getDueDate(){
        return issue.getDueDate();
    }

    /**
     * Assignees getter
     * @return Assignees list (each assignee is string)
     */
    public List<String> getAssignees(){
        return issue.getAssignees();
    }

    /**
     * Priority getter
     * @return Priority integer
     */
    public Integer getPriority(){
        return issue.getPriority();
    }

    /**
     * Themes getter
     * @return List of Themes
     */
    public List<Theme> getThemes() {
        return issue.getThemes();
    }
    @Override
    public String getName(){
        return issue.getName();
    }
    @Override
    public String getDescription(){
        return issue.getDescription();
    }


    //setters
    @Override
    public void setName(String name){
        issue.setName(name);
    }
    @Override
    public void setDescription(String description){
        issue.setDescription(description);
    }

    /**
     * Issue deadline setter
     * @param date Deadline to be set
     */
    public void setDueDate(Calendar date){
        issue.setDueDate(date);
    }

    /**
     * List of assignees setter
     * @param users List of users
     */
    public void setAssignees(List<String> users){
        issue.setAssignees(users);
    }

    /**
     * Method that adds user to the users list
     * @param user User name to be added
     */
    public void addAssignee(String user){
        issue.addAssignee(user);
    }

    /**
     * Priority setter
     * @param priority Priority integer (1-10)
     */
    public void setPriority(int priority){
        issue.setPriority(priority);
    }

    /**
     * Themes list setter
     * @param themes List of themes that should be added
     */
    public void setThemes(List<Theme> themes){
        issue.setThemes(themes);
    }

    /**
     * Method that adds theme to the list of themes through controller wrapper
     * @param themeController Controller of the theme
     */
    public void addTheme(ThemeController themeController){
        issue.addTheme((Theme) themeController.entity);
    }

    /**
     * Method that sets issue instance
     * @param baseIssue Issue instance
     */
    public void setBaseIssue(IssueAbstract baseIssue){
        issue = baseIssue;
    }

    /**
     * Set date as a string
     * @return Date as String
     */
    public String getCalendarString(){
        return issue.getCalendarString();
    }

    /**
     * Model getter
     * @return Issue instance
     */
    public IssueAbstract getIssue(){
        return issue;
    }
}
