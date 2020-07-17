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

    public Category(String name,ArrayList<String> attributes,String parent) {
        this.name = name;
        this.attributes=attributes;
        this.parent=parent;
    }

    public Category(String name,ArrayList<String> attributes) {
        this.name = name;
        this.attributes=attributes;
    }

    public Category(String name,String parent,ArrayList<String> allItemsID,ArrayList<String> attributes,ArrayList<String> subCategories){
        this.name = name;
        this.parent = parent;
        this.allItemsID = allItemsID;
        this.attributes = attributes;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }


    public void addAttribute(String attributeKey) {
        if (!attributes.contains(attributeKey))
            attributes.add(attributeKey);
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }


    public void addItem(String id) {
        if (!allItemsID.contains(id))
            allItemsID.add(id);
    }

    public boolean removeItem(String id) {
        if (allItemsID.contains(id)) {
            allItemsID.remove(id);
            return true;
        }
        return false;
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

    public void addSubCategory(String category) {
        if (subCategories.contains(category)) return;
        this.subCategories.add(category);
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
