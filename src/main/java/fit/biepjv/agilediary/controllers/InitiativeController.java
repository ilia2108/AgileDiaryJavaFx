package fit.biepjv.agilediary.controllers;


import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.Initiative;
import fit.biepjv.agilediary.models.IssueAbstract;

import java.util.List;

public class InitiativeController extends IssueControllerAbstract {

    public static class InitiativeControllerBuilder extends IssueControllerBuilderAbstract{
        public InitiativeControllerBuilder(){
            issueBuilder = new Initiative.InitiativeBuilder();
        }

        @Override
        public IssueControllerAbstract build(){
            return new InitiativeController(this);
        }
    }

    public InitiativeController(){
        issue = new Initiative();
    }
    public InitiativeController(InitiativeControllerBuilder builder){
        super(builder);
    }
    public List<Epic> getIncludedIssuesList(){
        return (List<Epic>) issue.getSubIssues();
    }

    public void setIncludedIssuesList(List<? extends IssueAbstract> epics){
        issue.setSubIssues(epics);
    }
    public void addIssue(IssueAbstract epic){
        issue.addIssue(epic);
    }

}
