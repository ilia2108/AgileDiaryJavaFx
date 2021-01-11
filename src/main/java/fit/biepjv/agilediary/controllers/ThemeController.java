package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ThemeController extends BaseControllerAbstract {

    /** <b>Theme Controller Builder class</b>
     * This class builds the Theme Controller.
     */
    public static class ThemeControllerBuilder extends BaseControllerBuilderAbstract{
        /**
         * Default constructor
         */
        public ThemeControllerBuilder(){
            entityBuilder = new Theme.ThemeBuilder();
        }

        @Override
        public BaseControllerAbstract build() {
            return new ThemeController(this);
        }
    }

    /**
     * Default constructor
     */
    public ThemeController(){
        entity = new Theme();
    }

    /**
     * Constructor based on Theme instance
     * @param theme Theme instance
     */
    public ThemeController(Theme theme) {
        entity = theme;
    }

    /**
     * Constructor based on controller builder
     * @param builder Theme Controller
     */
    public ThemeController(ThemeControllerBuilder builder){
        super(builder);
    }

    @Override
    public List<? extends IssueControllerAbstract> getIncludedIssuesList(){
        return new ArrayList<>();
    }
    @Override
    public void setIncludedIssuesList(List<? extends IssueAbstract> list){ }
    @Override
    protected void addIssue(IssueAbstract issue){
        entity.setName(issue.getName());
        entity.setDescription(issue.getDescription());
    }
    @Override
    public void addIssueController(IssueControllerAbstract issueController) { }

}