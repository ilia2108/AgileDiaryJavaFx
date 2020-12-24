package fit.biepjv.agilediary.controller;

import fit.biepjv.agilediary.model.IssueAbstract;
import fit.biepjv.agilediary.model.Theme;

import java.util.Calendar;
import java.util.List;

public abstract class IssueControllerAbstract extends BaseControllerAbstract {
    protected IssueAbstract issue = null;

    public Calendar getDueDate(){
        return issue.getDueDate();
    }
    public List<String> getAssignees(){
        return issue.getAssignees();
    }
    public Integer getPriority(){
        return issue.getPriority();
    }
    public List<Theme> getThemes() {
        return issue.getThemes();
    }
    public String getName(){
        return issue.getName();
    }
    public String getDescription(){
        return issue.getDescription();
    }
}
