package View.Menus;

import Control.ItemAndCategoryController;

public class ItemMenu extends Menu {
    private static ItemMenu itemMenu;
    private String itemID;
    private int optionCount = 4;
    private ItemMenu(){ }

    public static ItemMenu getInstance(){
        if(itemMenu==null)
            itemMenu = new ItemMenu();
        return itemMenu;
    }

    @Override
    public void run(){
        System.out.println(View.ANSI_BLUE + "You are in the item menu." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command){
      if(command.equals("digest")){
          digest();
      }
      if(command.equals("attributes")){
          attributes();
      }
    }

    @Override
    public void help(){
     System.out.println("digest");  //done
     System.out.println("add to cart");
     System.out.println("attributes");  //done
     System.out.println("compare [product id]");
     System.out.println("comments");
    }

    public void digest(){
      System.out.println(ItemAndCategoryController.getInstance().digest(itemID));
    }

    public void addToCart(){

    }

    public void attributes(){
     System.out.println(ItemAndCategoryController.getInstance().showAttributes(itemID));
    }

    public void compare(){

    }

    public void comments(){

    }

    public void addComment(){

    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
