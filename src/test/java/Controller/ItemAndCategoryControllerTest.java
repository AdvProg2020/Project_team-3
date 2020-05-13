package Controller;

import Model.Category;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAndCategoryControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void deleteItem() {
    }

    @Test
    public void isThereItemWithId() {
    }

    @Test
    public void isThereCategoryWithName() {
    }

    @Test
    public void getItemById() {
    }

    @Test
    public void getCategoryByName() {
    }

    @Test
    public void searchItemInCategory() {
    }

    @Test
    public void compare() {
    }

    @Test
    public void showItemComments() {
    }

    @Test
    public void comment() {
        UserController.getInstance().login("Reza","Rail");
        String text="this is the best item in the world!";
        ItemAndCategoryController.getInstance().comment(text,"uCe78");
    }

    @Test
    public void rate() {
    }

    @Test
    public void digest() {
    }

    @Test
    public void showAttributes() {
    }

    @Test
    public void addCategory() {
        ArrayList<String>attributes=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("lavazem manzel",attributes);

    }

    @Test
    public void testAddCategory() {
    }

    @Test
    public void sortBy() {
    }

    @Test
    public void filterBy() {
    }

    @Test
    public void addItem() {
        UserController.getInstance().login("Reza","Rail");
        ArrayList<String>attributeKey=new ArrayList<>();
        HashMap<String ,String> attributes=new HashMap<>();
        ItemAndCategoryController.getInstance().addItem("Vaccum","Reza",
                "this is nice",400,300,"lavazem manzel",attributeKey
        ,attributes);

    }

    @Test
    public void addItemToCategory() {
        Assert.assertTrue(ItemAndCategoryController.getInstance().isThereCategoryWithName("lavazem manzel"));
        Category category=ItemAndCategoryController.getInstance().getCategoryByName("lavazem manzel");
        //category.addItem();
    }

    @Test
    public void getCurrentCategory() {
    }

    @Test
    public void getCurrentViewableItems() {
    }

    @Test
    public void getCurrentViewableItemsString() {
    }

    @Test
    public void setViewableToCategory() {
    }

    @Test
    public void getCategoryItems() {
    }

    @Test
    public void getCategoryItemsString() {
    }

    @Test
    public void getBaseCategory() {
    }

    @Test
    public void previousCategory() {
    }

    @Test
    public void openCategory() {
    }

    @Test
    public void currentViewableItemsContainsItem() {
    }

    @Test
    public void getAllItemFromDataBase() {
    }

    @Test
    public void editCategoryName() {
        String newName="Home Appliances";
        ItemAndCategoryController.getInstance().editCategoryName("lavazem manzel",newName);
    }

    @Test
    public void removeCategory() {
    }

    @Test
    public void getInSaleItems() {
    }
}