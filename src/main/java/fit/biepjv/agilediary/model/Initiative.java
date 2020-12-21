package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.List;

public class Initiative extends IssueAbstract{
    List<Epic> epics;

    public Initiative(){
        super();
        epics = new ArrayList<>();
    }

    public List<Epic> getEpics() {
        return epics;
    }

    public void setEpics(List<Epic> epics) {
        setUpdatedAt();
        this.epics = epics;
    }
    public void addEpic(Epic epic){
        setUpdatedAt();
        epics.add(epic);
    }
}
