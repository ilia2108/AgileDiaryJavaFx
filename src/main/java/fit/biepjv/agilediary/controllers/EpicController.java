package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

import java.util.ArrayList;
import java.util.List;

public class EpicController extends IssueControllerAbstract{

    public static class EpicControllerBuilder extends IssueControllerBuilderAbstract{
        public EpicControllerBuilder(){
            issueBuilder = new Epic.EpicBuilder();
        }
        @Override
        public IssueControllerAbstract build() {
            return new EpicController(this);
        }
    }

    public EpicController(){
        issue = new Epic();
    }
    public EpicController(EpicControllerBuilder builder){
        super(builder);
    }
    public EpicController(Epic epic){
        issue = epic;
    }


    public List<StoryController> getIncludedIssuesList(){
        List<StoryController> result = new ArrayList<>();
        for(Story story: (List<Story>)issue.getSubIssues()){
            result.add(new StoryController(story));
        }
        return result;
    }

    public void setIncludedIssuesList(List<? extends IssueAbstract> stories){
        issue.setSubIssues(stories);
    }
    protected void addIssue(IssueAbstract story){
        issue.addIssue(story);
    }

    @Override
    public void addIssueController(IssueControllerAbstract issueController) {
        if(issueController instanceof StoryController)
            issue.addIssue(issueController.issue);
    }
}
