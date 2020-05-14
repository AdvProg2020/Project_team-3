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

//edit category

//edit sale
//darkhast ezafeye sell





//MOHEM: har item dar aane vahed mitone toye 1 sale bashe va sale motalegh be foroshande hast!

//teste addSale fail shod chon ba method e kosshere kiri ke hazf kardam neveshte shode bod kir tosh miram pakesh mikonam

//age login nabod khast karaye khass bokone redirect beshe be loginRegisterMenu na inke serfan error begire  (safhe 18 doc khate 3,4)

//bahs edit item ham bayad dorost beshe yani barasas attribute haye category anjam beshe

//SaleAndDiscountCodeController.getInstance().giveRandomDiscountCode();
//age kharid balaye 1 milion bod ye code takhfif ashghal be karbar hedie dade beshe (5% ba saghfe ashghali dar hade chosmesghal)





//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
//loginregistermenu intendedmenu enum beshe
