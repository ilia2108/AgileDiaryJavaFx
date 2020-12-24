package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*! \class Basic Issue class
    \brief Abstract class for all issues\

    This is the most basic class. It contains all general information about issue.
    It will be extended with actual subtypes of issues (e.g. Epic, Initiative etc.)
 */
public abstract class IssueAbstract extends EntityAbstract{
    protected Calendar createdAt; /// date of issue creation
    protected Calendar updatedAt; /// date of last issue update
    protected Calendar dueDate; /// Deadline date of issue.
    protected List<String> assignees; /// List of assignees (better to separate to object)
    protected Integer priority; /// priority of issue (1-10)
    protected List<Theme> themes; /// list of themes which are represented by the issue

    protected IssueAbstract(){
        super();
        assignees = new ArrayList<>();
        themes = new ArrayList<>();
        createdAt = Calendar.getInstance();
    }
    protected IssueAbstract(String name){
        super(name);
        assignees = new ArrayList<>();
        themes = new ArrayList<>();
        createdAt = Calendar.getInstance();
    }
    protected IssueAbstract(String name, String description){
        super(name, description);
        assignees = new ArrayList<>();
        themes = new ArrayList<>();
        createdAt = Calendar.getInstance();
    }

    //getters
    public Calendar getDueDate(){
        return dueDate;
    }
    public List<String> getAssignees(){
        return assignees;
    }
    public Integer getPriority(){
        return priority;
    }
    public List<Theme> getThemes() {
        return themes;
    }
    @Override
    public String getName() {
        setUpdatedAt();
        return super.getName();
    }
    @Override
    public String getDescription() {
        setUpdatedAt();
        return super.getDescription();
    }
    

    //setters
    @Override
    public void setName(String name) {
        super.setName(name);
    }
    @Override
    public void setDescription(String description){
        setUpdatedAt();
        super.setDescription(description);
    }
    public void setDueDate(Calendar dueDate){
        setUpdatedAt();
        this.dueDate = dueDate;
    }
    public void setAssignees(List<String> assignees){
        setUpdatedAt();
        this.assignees = assignees;
    }
    public void addAssignee(String user){
        setUpdatedAt();
        assignees.add(user);
    }
    public void setPriority(Integer priority){
        setUpdatedAt();
        this.priority = priority;
    }
    public void setThemes(List<Theme> themes) {
        setUpdatedAt();
        this.themes = themes;
    }
    public void addTheme(Theme theme){
        setUpdatedAt();
        themes.add(theme);
    }

    /// internal setter
    protected void setUpdatedAt(){
        updatedAt = Calendar.getInstance();
    }
}
