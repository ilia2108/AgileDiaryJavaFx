package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

import java.util.ArrayList;
import java.util.List;

/** <b>Story Controller class</b>
 * This class stands for controlling the Story instance.
 */
public class StoryController extends IssueControllerAbstract{

    /** <b>Story Controller Builder class</b>
     * This class stands for building new Story Controller instance.
     */
    public static class StoryControllerBuilder extends IssueControllerBuilderAbstract{
        /**
         * Default constructor
         */
        public StoryControllerBuilder(){
            issueBuilder = new Story.StoryBuilder();
        }

        @Override
        public IssueControllerAbstract build() {
            return new StoryController(this);
        }
    }

    /**
     * Default constructor
     */
    public StoryController(){
        issue = new Story();
    }

    /**
     * Constructor based on builder
     * @param builder Story Controller builder
     */
    public StoryController(StoryControllerBuilder builder){
        super(builder);
    }

    /**
     * Builder based on model instance
     * @param story {@code Story} instance
     */
    public StoryController(Story story){
        issue = story;
    }

    @Override
    public List<StoryController> getIncludedIssuesList(){
        List<StoryController> result = new ArrayList<>();
        for(Story story: (List<Story>)issue.getSubIssues()){
            result.add(new StoryController(story));
        }
        return result;
    }

    @Override
    public void setIncludedIssuesList(List<? extends IssueAbstract> substories){
        issue.setSubIssues(substories);
    }
    @Override
    protected void addIssue(IssueAbstract substory){
        issue.addIssue(substory);
    }

    @Override
    public void addIssueController(IssueControllerAbstract issueController) {
        if(issueController instanceof StoryController)
            addIssue(issueController.issue);
    }
}
