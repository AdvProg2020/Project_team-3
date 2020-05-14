import Controller.Database;
import Controller.ItemAndCategoryController;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.View;

public class Main {
    public static void main(String[] args) {
        Database.getInstance().initiate();
        ShopMenu.getInstance().setCurrentCategory(ItemAndCategoryController.getInstance().getBaseCategory().getName());
        View.run();
    }
}

//edit discount code
//edit category <= bade inke paiini anjam beshe mishe ino anjam dad

//moshahede kharidaran yek mahsol
//selller menu kheili ridmane
//darkhast ezafeye sell

//bahs edit item ham bayad dorost beshe yani barasas attribute haye category anjam beshe

//discount code , sale , log ha <= time daran
//method e passtime tuye view => miad discountcode haye expire shodaro hazf mikone va besorate random be karbara code takhfif ba darsad random o ina mide
//age kharid balaye 1 milion bod ye code takhfif ashghal be karbar hedie dade beshe (5% ba saghfe ashghali dar hade chosmesghal)

//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
