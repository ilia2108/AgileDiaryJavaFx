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
        /**
         * Name of the Theme
         */
        String themeName;

        /**
         *  Description of the Theme
         */
        String themeDescription;

        /**
         * Name setter
         * @param name Name the needs to be set in the Builder
         * @return The same object with changed property
         */
        public ThemeBuilder name(String name){
            themeName = name;
            return this;
        }

        /**
         * Description setter
         * @param description Description of the Theme that needs to be set to the Builder
         * @return The same object with changed property
         */
        public ThemeBuilder description(String description){
            themeDescription = description;
            return this;
        }

        /**
         * Build method
         * @return Object that was build
         */
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
        super(builder.themeName, builder.themeDescription);
    }
}
