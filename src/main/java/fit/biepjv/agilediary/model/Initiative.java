package fit.biepjv.agilediary.model;

import java.util.ArrayList;
import java.util.List;

/*  \class Initiative class
    \brief Biggest class in issues hierarchy

    This class describes the biggest abstraction layer called initiative. This is a goal that can be reached
    by implementing at least one epic - set of tasks related to some topic, which epic describes.
 */
public class Initiative extends IssueAbstract{
    List<Epic> epics; /// list of epics in initiative

    public Initiative(){
        super();
        epics = new ArrayList<>();
    }

    //getters
    public List<Epic> getEpics() {
        return epics;
    }

    //setters
    public void setEpics(List<Epic> epics) {
        setUpdatedAt();
        this.epics = epics;
    }
    public void addEpic(Epic epic){
        setUpdatedAt();
        epics.add(epic);
    }
}
