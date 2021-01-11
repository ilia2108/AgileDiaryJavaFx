package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Story;

import java.util.ArrayList;
import java.util.List;

/** <b>Epic Controller class</b>
 * This class stands for controlling the instance of {@code Epic}
 */
public class EpicController extends IssueControllerAbstract{

    /**<b>Epic Controller Builder class</b>
     * This stands for building new instance of Epic Controller.
     */
    public static class EpicControllerBuilder extends IssueControllerBuilderAbstract{
        /**
         * Default constructor
         */
        public EpicControllerBuilder(){
            issueBuilder = new Epic.EpicBuilder();
        }
        @Override
        public IssueControllerAbstract build() {
            return new EpicController(this);
        }
    }

    /**
     * Default constructor
     */
    public EpicController(){
        issue = new Epic();
    }

    /**
     * Constructor based on builder
     * @param builder Epic Controller builder instance
     */
    public EpicController(EpicControllerBuilder builder){
        super(builder);
    }

    /**
     * Constructor based on Model object
     * @param epic {@code Epic} instance
     */
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
            addIssue(issueController.issue);
    }
}
