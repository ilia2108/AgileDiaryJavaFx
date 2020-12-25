package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.EntityAbstract;
import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeController extends BaseControllerAbstract {

    public static class ThemeControllerBuilder extends BaseControllerBuilderAbstract{
        public ThemeControllerBuilder(){
            entityBuilder = new Theme.ThemeBuilder();
        }

        @Override
        public BaseControllerAbstract build() {
            return new ThemeController(this);
        }
    }

    public ThemeController(){
        entity = new Theme();
    }
    public ThemeController(ThemeControllerBuilder builder){
        super(builder);
    }

    public List<? extends IssueAbstract> getIncludedIssuesList(){
        return new ArrayList<>();
    }
    public void setIncludedIssuesList(List<? extends IssueAbstract> list){ }
    public void addIssue(IssueAbstract issue){
        entity.setName(issue.getName());
        entity.setDescription(issue.getDescription());


    }

}