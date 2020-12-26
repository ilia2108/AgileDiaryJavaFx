package fit.biepjv.agilediary.controllers;

import fit.biepjv.agilediary.models.EntityAbstract;
import fit.biepjv.agilediary.models.IssueAbstract;

import java.util.List;

public abstract class BaseControllerAbstract {
    protected EntityAbstract entity = null;

    public static abstract class BaseControllerBuilderAbstract{
        public EntityAbstract.EntityBuilderAbstract entityBuilder;

        public abstract BaseControllerAbstract build();
    }

    public BaseControllerAbstract(BaseControllerBuilderAbstract builder){
        entity = builder.entityBuilder.build();
    }
    public BaseControllerAbstract(){ }

    //getters
    public String getName(){
        return entity.getName();
    }

    public String getDescription(){
        return entity.getDescription();
    }

    public abstract List<? extends IssueControllerAbstract> getIncludedIssuesList();

    //setters
    public void setName(String name){
        entity.setName(name);
    }

    public void setDescription(String description){
        entity.setDescription(description);
    }

    public abstract void setIncludedIssuesList(List<? extends IssueAbstract> list);
    protected abstract void addIssue(IssueAbstract issue);
    public abstract void addIssueController(IssueControllerAbstract issueController);
}
