package Project.Client.Model;



import java.util.ArrayList;

public class Category {
    private String name;
    private String parent;
    private ArrayList<String> allItemsID = new ArrayList<>();
    private ArrayList<String> attributes = new ArrayList<>();
    private ArrayList<String> subCategories = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }


    public void addItem(String id) {
        if (!allItemsID.contains(id))
            allItemsID.add(id);
    }

    public ArrayList<String> getAllItemsID() {
        return allItemsID;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }


    public void setName(String newName){
        this.name=newName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", allItemsID=" + allItemsID +
                ", attributes=" + attributes +
                ", subCategories=" + subCategories +
                '}';
    }
}
