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
// bayad vaghti migim item tuye sale hast (salemenu) va vaghti miaim gheymatesho kam mikonim , beine starttime o endtime bashim!!!! <= 0317

// mogheii ke sale haro migirim bayad onaii ke accepted hastan ro bede na hammashono !!!! (???)


//edit category : add attribute [attribute e space dar]

//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
//loginregistermenu intendedmenu enum beshe