package fit.biepjv.agilediary.models;

/**
 * <b>Abstract Entity class</b>
 * Class describing the most basic entity in the Model
 *
 * This is the most basic class. It contains just name and description of each entity.
 * The reason for that is to separate Theme and all other stuff, which are issues.
 */
public abstract class EntityAbstract {
    /**
     * Name of entity
     */
    protected String name="";

    /**
     * Description of entity
     */
    protected String description="";

    /**
     * <b>Abstract builder of entity</b>
     *
     * This class stands for building the entity.
     * It's abstract, because the entity is also abstract.
     */
    public static abstract class EntityBuilderAbstract{

        /**
         * Name of field
         */
        protected String fieldName;

        /**
         * Description of field
         */
        protected String fieldDescription;

        /**
         * Name setter
         * @param name Name the needs to be set in the Builder
         * @return The same object with changed property
         */
        public abstract EntityBuilderAbstract name(String name);

        /**
         * Description setter
         * @param description Description of the Theme that needs to be set to the Builder
         * @return The same object with changed property
         */
        public abstract EntityBuilderAbstract description(String description);

        /**
         * Method that build object
         * @return Object that is built
         */
        public abstract EntityAbstract build();
    }

    /**
     * Default constructor
     */
    protected EntityAbstract() { }

    /**
     * Constructor based on the name
     * @param name Name that needs to be set
     */
    protected EntityAbstract(String name){
        this.name = name;
    }

    /**
     * Constructor based on name and description
     * @param name Name to be set
     * @param description Description to be set
     */
    protected EntityAbstract(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor based on builder
     * @param builder Entity Builder
     */
    protected EntityAbstract(EntityBuilderAbstract builder){
        this.name = builder.fieldName;
        this.description = builder.fieldDescription;
    }

    //getters

    /**
     * Name getter
     * @return Name of entity
     */
    public String getName() {
        return name;
    }

    /**
     * Description setter
     * @return Description of entity
     */
    public String getDescription() {
        return description;
    }

    //setters

    /**
     * Name setter
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description setter
     * @param description New description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
