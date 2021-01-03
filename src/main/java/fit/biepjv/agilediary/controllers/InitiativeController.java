package fit.biepjv.agilediary.controllers;


import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.Initiative;
import fit.biepjv.agilediary.models.IssueAbstract;

import java.util.ArrayList;
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
    public InitiativeController(Initiative initiative){
        super(initiative);
    }


    public List<EpicController> getIncludedIssuesList(){
        List<EpicController> result = new ArrayList<>();
        for(Epic epic: (List<Epic>)issue.getSubIssues()){
            result.add(new EpicController(epic));
        }
        return result;
    }

    public void setIncludedIssuesList(List<? extends IssueAbstract> epics){
        issue.setSubIssues(epics);
    }
    protected void addIssue(IssueAbstract epic){
        issue.addIssue(epic);
    }

    @Override
    public void addIssueController(IssueControllerAbstract issueController) {
        if(issueController instanceof EpicController)
            addIssue(issueController.issue);
    }
}
