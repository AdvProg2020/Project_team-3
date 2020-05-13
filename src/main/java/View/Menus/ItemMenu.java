package View.Menus;

import Controller.CartController;
import Controller.ItemAndCategoryController;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ItemMenu extends Menu {
    private static ItemMenu itemMenu;
    private String itemID;
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
            return;
        }
        if (command.equals("attributes")) {
            attributes();
            return;
        }
        if (command.equals("add to cart")) {
            addToCart();
            return;
        }
        if (command.equals("comments")) {
            comments();
            return;
        }
        if (command.equals("add comment")) {
            addComment();
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        if (command.equals("back")){
            back();
            return;
        }

        Matcher matcher=View.getMatcher("compare (\\S+)",command);
        if(matcher.matches()){
            compare(matcher.group(1));
            return;
        }
        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);

    }

    @Override
    public void help() {
        System.out.println(View.ANSI_PURPLE+"You are viewing an item (ID:"+itemID+"). Enter your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("digest");  //done
        System.out.println("add to cart"); //done
        System.out.println("attributes");  //done
        System.out.println("compare [product id]");  //done
        System.out.println("comments");  //done
        System.out.println("add comment"); //done
    }

    public void digest() {
        System.out.println(ItemAndCategoryController.getInstance().digest(itemID));
    }

    public void addToCart() {
        System.out.println(CartController.getInstance().addItemToCart(itemID));
    }

    public void attributes() {
        System.out.println(ItemAndCategoryController.getInstance().showAttributes(itemID));
    }

    public void compare(String id) {
        System.out.println(ItemAndCategoryController.getInstance().compare(itemID,id));
    }

    public void comments() {
        ArrayList<String> allComment = ItemAndCategoryController.getInstance().showItemComments(itemID);
        if (allComment.isEmpty()) {
            System.out.println(View.ANSI_RED+"This item doesn't have any comments"+View.ANSI_RESET);
        }
        for (String comment : allComment) {
            System.out.println(comment);
        }
    }

    public void addComment() {
        System.out.println("Please enter your comment or enter exit to go back.");
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
