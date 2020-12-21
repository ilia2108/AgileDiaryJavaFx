package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*! \class Basic Issue class
    \brief Abstract class for all issues\

    This is the most basic class. It contains all general information about issue.
    It will be extended with actual subtypes of issues (e.g. Epic, Initiative etc.)
 */
public abstract class IssueAbstract {
    protected String name;
    protected String description;
    protected Calendar createdAt;
    protected Calendar updatedAt;
    protected Calendar dueDate;
    protected List<String> assignees;
    protected Integer priority;

    public IssueAbstract(){
        assignees = new ArrayList<>();
        createdAt = Calendar.getInstance();
    }

    //getters
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Calendar getDueDate(){
        return dueDate;
    }
    public List<String> getAssignees(){
        return assignees;
    }
    public Integer getPriority(){
        return priority;
    }

    //setters
    public void setName(String name){
        setUpdatedAt();
        this.name = name;
    }
    public void setDescription(String description){
        setUpdatedAt();
        this.description = description;
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
        this.priority = priority;
    }

    protected void setUpdatedAt(){
        updatedAt = Calendar.getInstance();
    }
}
