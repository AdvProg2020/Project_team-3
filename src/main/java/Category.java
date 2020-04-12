import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private Category parent;
    private ArrayList<String> allItemsID = new ArrayList<>();
    private ArrayList<String> attributes = new ArrayList<>();
    private ArrayList<Category> subCategories = new ArrayList<>();
    public boolean hasItemWithID(String id){
        return allItemsID.contains(id);
    }

    public Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Category getParent() {
        return parent;
    }

    public void addAttribute(String attributeKey){
        if(!attributes.contains(attributeKey))
            attributes.add(attributeKey);
    }

    public void addItem(String id){
        if(!allItemsID.contains(id))
            allItemsID.add(id);
    }

    public boolean removeItem(String id){
        if(allItemsID.contains(id)) {
            allItemsID.remove(id);
            return true;
        }
        return false;
    }

    public boolean hasSubCategoryWithName(String name){
        if(subCategories.isEmpty()) return false;
        for(Category category:subCategories){
            if(category.getName().equals(name)) return true;
        }
        return false;
    }

    public Category getSubCategoryByName(String name){
        if(subCategories.isEmpty()) return null;
        for(Category category:subCategories){
            if(category.getName().equals(name)) return category;
        }
        return null;
    }
}
