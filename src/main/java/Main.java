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

//edit discount code
//edit category
//item hameye attribute haye category dashte bashahd  //mirza
//moshahade sabeghe forosh              //mirza
//moshahade buy log     //mirza

//moshahede kharidaran yek mahsol
//selller menu kheili ridmane
//darkhast ezafeye sell

//eslah discount code
//didan discount code haye karbar
// adam tavanayi estefade az code takhfif bad az chand bar

//moshahede vizhegi haye mahsol //mirza
//filter bar asas in stock      //mirza
//filter bar asas esm           //mirza
//filter bar asas attribute     //mirza


//safhe haraj ride

