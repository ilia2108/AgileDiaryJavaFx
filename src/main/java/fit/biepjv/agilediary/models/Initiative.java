package fit.biepjv.agilediary.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*  \class Initiative class
    \brief Biggest class in issues hierarchy

    This class describes the biggest abstraction layer called initiative. This is a goal that can be reached
    by implementing at least one epic - set of tasks related to some topic, which epic describes.
 */
public class Initiative extends IssueAbstract{
    List<Epic> epics; /// list of epics in initiative

    public static class InitiativeBuilder extends IssueBuilderAbstract{
        List<Epic> fieldEpicsList = new ArrayList<>();

        public InitiativeBuilder epics(List<Epic> epics){
            fieldEpicsList = epics;
            return this;
        }
        public InitiativeBuilder addEpic(Epic epic){
            fieldEpicsList.add(epic);
            return this;
        }

        public Initiative build(){
            return new Initiative(this);
        }
    }


    public Initiative(){
        super();
        epics = new ArrayList<>();
    }
    public Initiative(String name){
        super(name);
        epics = new ArrayList<>();
    }
    public Initiative(String name, String description){
        super(name, description);
        epics = new ArrayList<>();
    }
    public Initiative(InitiativeBuilder builder){
        super(builder);
        epics = builder.fieldEpicsList;
    }

    //getters
    public List<Epic> getSubIssues(){
        return epics;
    }

    //setters
    public void setSubIssues(List<? extends IssueAbstract> epics){
        //setUpdatedAt();
        for(IssueAbstract epic: epics){
            addIssue(epic);
        }
    }
    public void addIssue(IssueAbstract issue){
        //setUpdatedAt();
        if(issue instanceof Epic)
            epics.add((Epic) issue);
    }
}
