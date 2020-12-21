package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.List;

public class Story extends IssueAbstract{
    List<Story> substories;

    public Story(){
        super();
        substories = new ArrayList<>();
    }

    public List<Story> getSubstories() {
        return substories;
    }


    public void setSubstories(List<Story> substories) {
        this.substories = substories;
    }
    public void addSubstory(Story substory){
        substories.add(substory);
    }
}
