package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryController extends IssueControllerAbstract{

    public static class StoryControllerBuilder extends IssueControllerBuilderAbstract{
        public StoryControllerBuilder(){
            issueBuilder = new Story.StoryBuilder();
        }

        @Override
        public IssueControllerAbstract build() {
            return new StoryController(this);
        }
    }

    public StoryController(){
        issue = new Story();
    }
    public StoryController(StoryControllerBuilder builder){
        super(builder);
    }
    public StoryController(Story story){
        issue = story;
    }

    public List<StoryController> getIncludedIssuesList(){
        //return (List<Story>) issue.getSubIssues();
        List<StoryController> result = new ArrayList<>();
        for(Story story: (List<Story>)issue.getSubIssues()){
            result.add(new StoryController(story));
        }
        return result;
    }

    public void setIncludedIssuesList(List<? extends IssueAbstract> substories){
        issue.setSubIssues(substories);
    }
    protected void addIssue(IssueAbstract substory){
        issue.addIssue(substory);
    }

    @Override
    public void addIssueController(IssueControllerAbstract issueController) {
        if(issueController instanceof StoryController)
            addIssue(issueController.issue);
    }
}
