package fit.biepjv.agilediary.models;

/**
    <b>Theme class</b>
    Class describing the Theme

    According to Agile methodology there's a biggest entity called theme.
    That could represent the company department or something bigger.
    Theme can be used further as a label for each issue.
 */
public class Theme extends EntityAbstract{

    /**
     * <b>Theme Builder class</b>
     * This is a class that is used to build a Theme class.
     */
    public static class ThemeBuilder extends EntityBuilderAbstract {

        @Override
        public EntityBuilderAbstract name(String name){
            fieldName = name;
            return this;
        }

        @Override
        public EntityBuilderAbstract description(String description){
            fieldDescription = description;
            return this;
        }

        @Override
        public EntityAbstract build(){
            return new Theme(this);
        }
    }

    /**
     * Default Theme constructor
     */
    public Theme(){
        super();
    }

    /**
     * Theme constructor
     * @param builder Builder that is used to build object
     */
    public Theme(ThemeBuilder builder){
        super(builder.fieldName, builder.fieldDescription);
    }
}
