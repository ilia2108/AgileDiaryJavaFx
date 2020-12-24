package fit.biepjv.agilediary.controller;

import fit.biepjv.agilediary.model.EntityAbstract;

public abstract class BaseControllerAbstract {
    protected EntityAbstract entity = null;

    //getters
    public String getName(){
        return entity.getName();
    }

    public String getDescription(){
        return entity.getDescription();
    }

    //setters
    public void setName(String name){
        entity.setName(name);
    }

    public void setDescription(String description){
        entity.setDescription(description);
    }
}
