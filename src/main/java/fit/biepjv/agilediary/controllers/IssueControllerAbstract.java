package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;

import java.util.Calendar;
import java.util.List;

public abstract class IssueControllerAbstract extends BaseControllerAbstract {
    protected IssueAbstract issue = null;

    public IssueControllerAbstract(){}
//    public IssueControllerAbstract(IssueAbstract.IssueBuilderAbstract builder){
//        issue = builder.build();
//    }

    //getters
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
    public void setDueDate(Calendar date){
        issue.setDueDate(date);
    }
    public void setAssignees(List<String> users){
        issue.setAssignees(users);
    }
    public void addAssignee(String user){
        issue.addAssignee(user);
    }
    public void setPriority(int priority){
        issue.setPriority(priority);
    }
    public void setThemes(List<Theme> themes){
        issue.setThemes(themes);
    }
    public void addTheme(Theme theme){
        issue.addTheme(theme);
    }
    public void setBaseIssue(IssueAbstract baseIssue){
        issue = baseIssue;
    }
}
