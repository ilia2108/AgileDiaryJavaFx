package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

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

    public List<Story> getIncludedIssuesList(){
        return (List<Story>) issue.getSubIssues();
    }

    public void setIncludedIssuesList(List<? extends IssueAbstract> substories){
        issue.setSubIssues(substories);
    }
    public void addIssue(IssueAbstract substory){
        issue.addIssue(substory);
    }
}
