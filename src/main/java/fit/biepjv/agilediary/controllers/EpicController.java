package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

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

    public List<Story> getIncludedIssuesList(){
        return (List<Story>) issue.getSubIssues();
    }

    public void setIncludedIssuesList(List<? extends IssueAbstract> stories){
        issue.setSubIssues(stories);
    }
    public void addIssue(IssueAbstract story){
        issue.addIssue(story);
    }
}
