package fit.biepjv.agilediary.controllers;


import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.Initiative;
import fit.biepjv.agilediary.models.IssueAbstract;

import java.util.ArrayList;
import java.util.List;

/** <b>Initiative Controller Class</b>
 * This class stands for Initiative Controller.
 */
public class InitiativeController extends IssueControllerAbstract {

    /** <b>Initiative Controller Builder class</b>
     * This class builds new instance of initiative controller.
     */
    public static class InitiativeControllerBuilder extends IssueControllerBuilderAbstract{
        /**
         * Default constructor
          */
        public InitiativeControllerBuilder(){
            issueBuilder = new Initiative.InitiativeBuilder();
        }

        @Override
        public IssueControllerAbstract build(){
            return new InitiativeController(this);
        }
    }

    /**
     * Default constructor
     */
    public InitiativeController(){
        issue = new Initiative();
    }

    /**
     * Constructor based on controller builder
     * @param builder Initiative Controller Builder
     */
    public InitiativeController(InitiativeControllerBuilder builder){
        super(builder);
    }

    /**
     * Constructor based on model instance
     * @param initiative Initiative instance
     */
    public InitiativeController(Initiative initiative){
        super(initiative);
    }

    @Override
    public List<EpicController> getIncludedIssuesList(){
        List<EpicController> result = new ArrayList<>();
        for(Epic epic: (List<Epic>)issue.getSubIssues()){
            result.add(new EpicController(epic));
        }
        return result;
    }

    @Override
    public void setIncludedIssuesList(List<? extends IssueAbstract> epics){
        issue.setSubIssues(epics);
    }

    @Override
    protected void addIssue(IssueAbstract epic){
        issue.addIssue(epic);
    }

    @Override
    public void addIssueController(IssueControllerAbstract issueController) {
        if(issueController instanceof EpicController)
            addIssue(issueController.issue);
    }
}
