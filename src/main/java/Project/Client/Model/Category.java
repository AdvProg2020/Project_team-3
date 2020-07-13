package Project.Client.Model;

import Server.Controller.Database;
import Server.Controller.ItemAndCategoryController;

import java.util.ArrayList;

public class Category {
    private String name;
    private String parent;
    private ArrayList<String> allItemsID = new ArrayList<>();
    private ArrayList<String> attributes = new ArrayList<>();
    private ArrayList<String> subCategories = new ArrayList<>();

    public boolean hasItemWithID(String id) {
        return allItemsID.contains(id);
    }



    public String getName() {
        return name;
    }



    public boolean hasSubCategoryWithName(String name) {
        return subCategories.contains(name);
    }



    public ArrayList<String> getAllItemsID() {
        return allItemsID;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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
