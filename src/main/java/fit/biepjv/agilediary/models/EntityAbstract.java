package fit.biepjv.agilediary.models;

/*
    \class Abstract Entity class
    \brief Class describing the most basic entity in the Model

    This is the most basic class. It contains just name and description of each entity.
    The reason for that is to separate Theme and all other stuff, which are issues.
 */
public abstract class EntityAbstract {
    protected String name=""; /// name of theme
    protected String description=""; /// short description

    protected EntityAbstract() { }
    protected EntityAbstract(String name){
        this.name = name;
    }
    protected EntityAbstract(String name, String description){
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
