package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;

import java.util.Calendar;
import java.util.List;

public abstract class IssueControllerAbstract extends BaseControllerAbstract {
    protected IssueAbstract issue = null;

    public static abstract class IssueControllerBuilderAbstract extends BaseControllerBuilderAbstract{
        public IssueAbstract.IssueBuilderAbstract issueBuilder;

        @Override
        public abstract IssueControllerAbstract build();

        public IssueControllerBuilderAbstract addThemeController(ThemeController themeController){
            issueBuilder.addTheme((Theme) themeController.entity);
            return this;
        }
    }

    public IssueControllerAbstract(){}
    public IssueControllerAbstract(IssueControllerBuilderAbstract builder){
       issue = (IssueAbstract) builder.issueBuilder.build();
    }
    public IssueControllerAbstract(IssueAbstract issue){
        this.issue = issue;
    }

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
    public void addTheme(ThemeController themeController){
        issue.addTheme((Theme) themeController.entity);
    }
    public void setBaseIssue(IssueAbstract baseIssue){
        issue = baseIssue;
    }
}
