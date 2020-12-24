package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.IssueAbstract;
import fit.biepjv.agilediary.models.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeController extends BaseControllerAbstract {
    public ThemeController(){
        entity = new Theme();
    }

    public List<? extends IssueAbstract> getIncludedIssuesList(){
        return new ArrayList<>();
    }
    public void setIncludedIssuesList(List<? extends IssueAbstract> list){ }
    public void addIssue(IssueAbstract issue){ }

}