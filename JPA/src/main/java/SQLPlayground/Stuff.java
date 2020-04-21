package SQLPlayground;


public class Stuff {

    private String name;
    private Person owner;
    private String description;

    public Stuff(String name, Person owner, String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner.getName();
    }

    public String getDescription() {
        return description;
    }
}
