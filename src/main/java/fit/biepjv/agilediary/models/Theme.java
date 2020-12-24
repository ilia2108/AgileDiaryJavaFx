package fit.biepjv.agilediary.models;

/*
    \class Theme class
    \brief Class describing the Theme

    According to Agile methodology there's a biggest entity called theme.
    That could represent the company department or something bigger.
    Theme can be used further as a label for each issue.
 */
public class Theme extends EntityAbstract{

    public Theme(){
        super();
    }
    public Theme(String name){
        super(name);
    }
    public Theme(String name, String description){
        super(name, description);
    }
}
