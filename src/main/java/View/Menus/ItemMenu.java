package View.Menus;

import Control.ItemAndCategoryController;

import java.util.ArrayList;

public class ItemMenu extends Menu {
    private static ItemMenu itemMenu;
    private String itemID;
    private int optionCount = 4;

    private ItemMenu() {
    }

    public static ItemMenu getInstance() {
        if (itemMenu == null)
            itemMenu = new ItemMenu();
        return itemMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_BLUE + "You are in the item menu." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.equals("digest")) {
            digest();
        }
        if (command.equals("attributes")) {
            attributes();
        }
        if (command.equals("add to cart")) {
            addToCart();
        }
        if (command.equals("comments")) {
            comments();
        }
        if (command.equals("add comment")) {
            addComment();
        }
        if (command.equals("help")) {
            help();
        }
    }

    @Override
    public void help() {
        System.out.println("digest");  //done
        System.out.println("add to cart"); //done
        System.out.println("attributes");  //done
        System.out.println("compare [product id]");
        System.out.println("comments");  //done
        System.out.println("add comment"); //done
    }

    public void digest() {
        System.out.println(ItemAndCategoryController.getInstance().digest(itemID));
    }

    public void addToCart() {
        System.out.println(ItemAndCategoryController.getInstance().addItemToCart(itemID));
    }

    public void attributes() {
        System.out.println(ItemAndCategoryController.getInstance().showAttributes(itemID));
    }

    public void compare() {

    }

    public void comments() {
        ArrayList<String> allComment = ItemAndCategoryController.getInstance().showItemComments(itemID);
        if (allComment.isEmpty()) {
            System.out.println("this item doesnt have any comments");
        }
        for (String comment : allComment) {
            System.out.println(comment);
        }
    }

    public void addComment() {
        System.out.println("please enter your comment or enter exit to go back");
        String comment = View.read.nextLine();
        if (comment.equals("exit")) {
            return;
        }
        System.out.println(ItemAndCategoryController.getInstance().comment(comment, itemID));
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
