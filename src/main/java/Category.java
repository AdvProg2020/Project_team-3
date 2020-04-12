import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private ArrayList<String> allItemsID = new ArrayList<>();
    private HashMap<String,String> attributes = new HashMap<>();
    private ArrayList<Category> subCategories = new ArrayList<>();

    public boolean hasItemWithID(String id){
        return allItemsID.contains(id);
    }

}
