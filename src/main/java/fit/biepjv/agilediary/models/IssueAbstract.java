package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** <b>Basic Issue class</b>
 * Abstract class for all issues
 *
 * This is one of the most basic class about Issues.
 * It contains all general information about issue.
 * It will be extended with actual subtypes of issues (e.g. Epic, Initiative etc.).
 * That's the basic class for all classes except <b>Theme</b>
 */
public abstract class IssueAbstract extends EntityAbstract{
    /**
     * Deadline date of issue.
     */
    protected Calendar dueDate;

    /**
     * List of assignees
     */
    protected List<String> assignees = new ArrayList<>();

    /**
     * Priority of issue (1-10). The lowest number - the less issue is important.
     */
    protected Integer priority;

    /**
     * List of themes which are represented by the issue
     */
    protected List<Theme> themes = new ArrayList<>();

    /**
     * String representation of date.
     */
    protected String calendarString;

    /**
     * <b>Abstract class of Issue Builder</b>
     * This is the builder that is used to build an object of Issue.
     * The inheritance hierarchy corresponds the Model inheritance.
     */
    public abstract static class IssueBuilderAbstract extends EntityAbstract.EntityBuilderAbstract{
        /**
         * Deadline date of issue.
         */
        protected Calendar fieldDueDate;

        /**
         * List of assignees (better to separate to object)
         */
        protected List<String> fieldAssignees = new ArrayList<>();

        /**
         * Priority of issue (1-10)
         */
        protected Integer fieldPriority;

        /**
         * List of Themes
         */
        protected List<Theme> fieldThemes = new ArrayList<>();

        /**
         *  String representation of date.
         */
        protected String calendarString;


        /**
         * Setter of string that represents date
         * @param year Year to be set
         * @param month Month to be set
         * @param day Day of the month to be set
         * @return Current Builder object
         */
        public IssueBuilderAbstract dueDateString(int year, int month, int day){
            calendarString = new StringBuilder()
                    .append(year)
                    .append("-")
                    .append(month)
                    .append("-")
                    .append(day)
                    .toString();
            return this;
        }

        /**
         * Setter of deadline for the issue
         * @param dueDate Date to be set as a deadline
         * @return  Current Builder object
         */
        public IssueBuilderAbstract dueDate(Calendar dueDate){
            fieldDueDate = dueDate;
            return this;
        }

        /**
         * Setter of assignees list
         * @param assignees List of assignees names
         * @return  Current Builder object
         */
        public IssueBuilderAbstract assignees(List<String> assignees){
            fieldAssignees = assignees;
            return this;
        }

        /**
         * Method that adds an assignee to the list
         * @param assignee Name of an assignee to be added
         * @return Current Builder object
         */
        public IssueBuilderAbstract addAssignee(String assignee){
            fieldAssignees.add(assignee);
            return this;
        }

        /**
         * Method that sets issue priority
         * @param priority Integer that represents priority
         * @return Current Builder object
         */
        public IssueBuilderAbstract priority(Integer priority){
            if(priority > 0 && priority <= 10)
                fieldPriority = priority;
            else fieldPriority = -1;
            return this;
        }

        /**
         * Method that sets list of themes that this issue is representing
         * @param themes List of themes
         * @return Current Builder object
         */
        public IssueBuilderAbstract themes(List<Theme> themes){
            fieldThemes = themes;
            return this;
        }

        /**
         * Method that adds a single theme to the list
         * @param theme Theme to be added
         * @return Current Builder object
         */
        public IssueBuilderAbstract addTheme(Theme theme){
            fieldThemes.add(theme);
            return this;
        }

        @Override
        public EntityBuilderAbstract name(String name) {
            fieldName = name;
            return this;
        }

        @Override
        public EntityBuilderAbstract description(String description) {
            fieldDescription = description;
            return this;
        }
    }

    /**
     * Default constructor
     */
    protected IssueAbstract(){
        super();
    }

    /**
     * Constructor based on issue name
     * @param name Issue name
     */
    protected IssueAbstract(String name){
        super(name);
    }

    /**
     * Constructor based on name and description
     * @param name Issue name
     * @param description Issue description
     */
    protected IssueAbstract(String name, String description){
        super(name, description);
    }

    /**
     * Constructor based on issue builder
     * @param builder Issue builder
     */
    protected IssueAbstract(IssueBuilderAbstract builder){
        super(builder);
        assignees = builder.fieldAssignees;
        themes = builder.fieldThemes;
        dueDate = builder.fieldDueDate;
        priority = builder.fieldPriority;
        calendarString = builder.calendarString;
    }

    //getters

    /**
     * Deadline date getter
     * @return Calendar object with deadline
     */
    public Calendar getDueDate(){
        return dueDate;
    }

    /**
     * Assignees list getter
     * @return List of assignees
     */
    public List<String> getAssignees(){
        return assignees;
    }

    /**
     * Issue priority getter
     * @return Priority as an integer 1-10.
     * The more integer is - the higher priority is.
     */
    public Integer getPriority(){
        return priority;
    }

    /**
     * Themes list getter
     * @return List of Themes
     */
    public List<Theme> getThemes() {
        return themes;
    }

    @Override
    public String getName() {
        return super.getName();
    }
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Method that gets list of issues that are included in existing one.
     * @return List of included issues
     */
    public abstract List<? extends IssueAbstract> getSubIssues();


    //setters
    @Override
    public void setName(String name) {
        super.setName(name);
    }
    @Override
    public void setDescription(String description){
        super.setDescription(description);
    }

    /**
     * Deadline date setter
     * @param dueDate Deadline date
     */
    public void setDueDate(Calendar dueDate){
        this.dueDate = dueDate;
    }

    /**
     * Assignees list setter. Each assignee is represented with a single string
     * @param assignees Assignees list
     */
    public void setAssignees(List<String> assignees){
        this.assignees = assignees;
    }

    /**
     * Method that adds an assignee to the list
     * @param user Assignee to be added
     */
    public void addAssignee(String user){
        assignees.add(user);
    }

    /**
     * Issue priority setter
     * @param priority Integer that represents priority from 1 to 10 inclusive.
     *                 The more integer is - the higher issue priority is.
     */
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    /**
     * Themes list setter
     * @param themes List of themes
     */
    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    /**
     * Method that adds a single theme to the themes list
     * @param theme Theme to be added
     */
    public void addTheme(Theme theme){
        themes.add(theme);
    }

    /**
     *  Deadline date as a string getter
     * @return Date as a string
     */
    public String getCalendarString() {
        return calendarString;
    }

    /**
     * Method that sets list of included issues
     * @param issues List of included issues
     */
    public abstract void setSubIssues(List<? extends IssueAbstract> issues);

    /**
     * Method that adds issue to the list of included ones of the current one
     * @param issue Issue to be included
     */
    public abstract void addIssue(IssueAbstract issue);
}
