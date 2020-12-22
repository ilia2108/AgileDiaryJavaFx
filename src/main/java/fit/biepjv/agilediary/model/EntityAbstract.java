package fit.biepjv.agilediary.model;

/*
    \class Abstract Entity class
    \brief Class describing the most basic entity in the Model

    This is the most basic class. It contains just name and description of each entity.
    The reason for that is to separate Theme and all other stuff, which are issues.
 */
public abstract class EntityAbstract {
    String name; /// name of theme
    String description; /// short description

    public EntityAbstract(){
        this.name = "";
        this.description = "";
    }
    public EntityAbstract(String name){
        this.name = name;
    }
    public EntityAbstract(String name, String description){
        this.name = name;
        this.description = description;
    }

    //getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
