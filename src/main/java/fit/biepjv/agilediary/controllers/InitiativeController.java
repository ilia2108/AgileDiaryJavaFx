package fit.biepjv.agilediary.controllers;


import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.Initiative;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

import java.util.List;

public class InitiativeController extends IssueControllerAbstract {
    public InitiativeController(){
        issue = new Initiative();
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
