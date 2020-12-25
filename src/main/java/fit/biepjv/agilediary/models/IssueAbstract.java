package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*! \class Basic Issue class
    \brief Abstract class for all issues\

    This is the most basic class. It contains all general information about issue.
    It will be extended with actual subtypes of issues (e.g. Epic, Initiative etc.)
 */
public abstract class IssueAbstract extends EntityAbstract{
    //protected Calendar createdAt = Calendar.getInstance(); /// date of issue creation
    //protected Calendar updatedAt; /// date of last issue update
    protected Calendar dueDate; /// Deadline date of issue.
    protected List<String> assignees = new ArrayList<>(); /// List of assignees (better to separate to object)
    protected Integer priority; /// priority of issue (1-10)
    protected List<Theme> themes = new ArrayList<>(); /// list of themes which are represented by the issue

    public abstract static class IssueBuilderAbstract extends EntityAbstract.EntityBuilderAbstract{
        Calendar fieldCreatedAt = Calendar.getInstance(); /// date of issue creation
        protected Calendar fieldUpdatedAt; /// date of last issue update
        protected Calendar fieldDueDate; /// Deadline date of issue.
        protected List<String> fieldAssignees = new ArrayList<>(); /// List of assignees (better to separate to object)
        protected Integer fieldPriority; /// priority of issue (1-10)
        protected List<Theme> fieldThemes = new ArrayList<>();

        public IssueBuilderAbstract createdAt(Calendar createdAt){
            fieldCreatedAt = createdAt;
            return this;
        }
        public IssueBuilderAbstract updatedAt(Calendar updatedAt){
            fieldUpdatedAt = updatedAt;
            return this;
        }
        public IssueBuilderAbstract dueDate(Calendar dueDate){
            fieldDueDate = dueDate;
            return this;
        }
        public IssueBuilderAbstract assignees(List<String> assignees){
            fieldAssignees = assignees;
            return this;
        }
        public IssueBuilderAbstract addAssignee(String assignee){
            fieldAssignees.add(assignee);
            return this;
        }
        public IssueBuilderAbstract priority(Integer priority){
            fieldPriority = priority;
            return this;
        }
        public IssueBuilderAbstract themes(List<Theme> themes){
            fieldThemes = themes;
            return this;
        }
        public IssueBuilderAbstract addTheme(Theme theme){
            fieldThemes.add(theme);
            return this;
        }

    }

    protected IssueAbstract(){
        super();
    }
    protected IssueAbstract(String name){
        super(name);
    }
    protected IssueAbstract(String name, String description){
        super(name, description);
    }
    protected IssueAbstract(IssueBuilderAbstract builder){
        super(builder);
        assignees = builder.fieldAssignees;
        themes = builder.fieldThemes;
        dueDate = builder.fieldDueDate;
        priority = builder.fieldPriority;
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
        //setUpdatedAt();
        return super.getName();
    }
    @Override
    public String getDescription() {
        //setUpdatedAt();
        return super.getDescription();
    }

    public abstract List<? extends IssueAbstract> getSubIssues();


    //setters
    @Override
    public void setName(String name) {
        super.setName(name);
    }
    @Override
    public void setDescription(String description){
        //setUpdatedAt();
        super.setDescription(description);
    }
    public void setDueDate(Calendar dueDate){
        //setUpdatedAt();
        this.dueDate = dueDate;
    }
    public void setAssignees(List<String> assignees){
        //setUpdatedAt();
        this.assignees = assignees;
    }
    public void addAssignee(String user){
        //setUpdatedAt();
        assignees.add(user);
    }
    public void setPriority(Integer priority){
        //setUpdatedAt();
        this.priority = priority;
    }
    public void setThemes(List<Theme> themes) {
        //setUpdatedAt();
        this.themes = themes;
    }
    public void addTheme(Theme theme){
        //setUpdatedAt();
        themes.add(theme);
    }

    public abstract void setSubIssues(List<? extends IssueAbstract> issues);
    public abstract void addIssue(IssueAbstract issue);


    /// internal setter
//    protected void setUpdatedAt(){
//        updatedAt = Calendar.getInstance();
//    }
}
