import Controller.Database;
import Controller.ItemAndCategoryController;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.View;

public class Main {
    public static void main(String[] args) {
        Database.getInstance().initiate();
        View.run();
    }
}

//edit category : add attribute [attribute e space dar]

//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
