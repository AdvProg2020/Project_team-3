package View.Menus;

public class PurchaseMenu extends Menu {
    private static PurchaseMenu purchaseMenu;

    private PurchaseMenu() {
    }

    public static PurchaseMenu getInstance() {
        if (purchaseMenu == null)
            purchaseMenu = new PurchaseMenu();
        return purchaseMenu;
    }

    public void run() {
    }

    public void execute(String command) {
    }

    public void help() {
    }
}
