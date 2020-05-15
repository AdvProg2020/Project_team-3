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
//request esmesh biad  mirza

//logout dar hamaje ye bar emtehan shaavd <= kolli


//edit category mirza

//edit sale arman

// filter by attribute biad esme attribute ro begire badesh biad meghdare begire o ina. mirza

//darkhast ezafeye sale arman

// mogheii ke sale haro migirim bayad onaii ke accepted hastan ro bede na hammashono !!!! (???)
// item o ina ham hamintor (???)

//SaleAndDiscountCodeController.getInstance().giveRandomDiscountCode(); arman
//age kharid balaye 1 milion bod ye code takhfif ashghal be karbar hedie dade beshe (5% ba saghfe ashghali dar hade chosmesghal) arman

//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
//loginregistermenu intendedmenu enum beshe
//mvc rayat shavad