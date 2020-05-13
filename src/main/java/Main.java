import ControllerTest.Database;
import ControllerTest.ItemAndCategoryController;
import View.Menus.ShopMenu.ShopMenu;
import View.Menus.View;

public class Main {
    public static void main (String[] args)  {
        ShopMenu.getInstance().setCurrentCategory(ItemAndCategoryController.getInstance().getBaseCategory());
        Database.getInstance().initiate();
        View.run();
    }
}
