package fit.biepjv.agilediary.models;

/*
    \class Theme class
    \brief Class describing the Theme

    According to Agile methodology there's a biggest entity called theme.
    That could represent the company department or something bigger.
    Theme can be used further as a label for each issue.
 */
public class Theme extends EntityAbstract{

    public static class ThemeBuilder{
        String fieldName;
        String fieldDescription;

        public ThemeBuilder name(String name){
            fieldName = name;
            return this;
        }
        public ThemeBuilder description(String description){
            fieldDescription = description;
            return this;
        }
        public Theme build(){
            return new Theme(this);
        }
    }

    public Theme(){
        super();
    }
    public Theme(ThemeBuilder builder){
        super(builder.fieldName, builder.fieldDescription);
    }
}
