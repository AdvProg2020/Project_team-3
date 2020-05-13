import Controller.Database;
import Controller.ItemAndCategoryController;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.View;

public class Main {
    public static void main (String[] args)  {
        Database.getInstance().initiate();
        ShopMenu.getInstance().setCurrentCategory(ItemAndCategoryController.getInstance().getBaseCategory().getName());
        View.run();
    }
}
