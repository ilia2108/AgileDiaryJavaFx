package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.EntityAbstract;
import fit.biepjv.agilediary.models.IssueAbstract;

import java.util.List;

public abstract class BaseControllerAbstract {
    protected EntityAbstract entity = null;

    //getters
    public String getName(){
        return entity.getName();
    }

    public String getDescription(){
        return entity.getDescription();
    }

    public abstract List<? extends IssueAbstract> getIncludedIssuesList();

    //setters
    public void setName(String name){
        entity.setName(name);
    }

    public void setDescription(String description){
        entity.setDescription(description);
    }

    public abstract void setIncludedIssuesList(List<? extends IssueAbstract> list);
    public abstract void addIssue(IssueAbstract issue);
}
