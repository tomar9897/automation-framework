package healing.models;

public class LocatorMetadata {

    private String tag;
    private String id;
    private String name;
    private String className;
    private String placeholder;
    private String type;
    private String text;

    public LocatorMetadata() {}

    public LocatorMetadata(String tag, String id, String name, String className,
                           String placeholder, String type, String text) {
        this.tag = tag;
        this.id = id;
        this.name = name;
        this.className = className;
        this.placeholder = placeholder;
        this.type = type;
        this.text = text;
    }

    public String getTag() { return tag; }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getClassName() { return className; }
    public String getPlaceholder() { return placeholder; }
    public String getType() { return type; }
    public String getText() { return text; }
}