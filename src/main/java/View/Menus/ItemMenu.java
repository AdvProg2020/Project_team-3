package View.Menus;

import Control.ItemAndCategoryController;

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
        } else if (command.equals("attributes")) {
            attributes();
        } else if (command.equals("add to cart")) {
            addToCart();
        } else if (command.equals("comments")) {
            comments();
        } else if (command.equals("add comment")) {
            addComment();
        } else if (command.equals("help")) {
            help();
        }else{
        Matcher matcher=View.getMatcher("compare (\\S+)",command);
        if(matcher.matches()){
            compare(matcher.group(1));
            return;
        }
        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
        }
    }

    @Override
    public void help() {
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
        System.out.println(ItemAndCategoryController.getInstance().addItemToCart(itemID));
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
