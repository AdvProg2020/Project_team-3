package View.Menus;

import Control.ItemAndCategoryController;
import Model.Category;

public class ShopMenu extends Menu {
    private static ShopMenu shopMenu;
    private Category currentCategory;
    private int optionCount = 6;
    private ShopMenu(){ }

    public static ShopMenu getInstance(){
        if(shopMenu==null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }

    @Override
    public void run(){
        System.out.println(View.ANSI_YELLOW+"You are in the shop menu."+View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if(command.equals("view categories")){

        }
        else if(command.equals("filtering")){

        }
        else if(command.equals("show available filters")){

        }
        else if(command.startsWith("filter ")){

        }
        else if(command.equals("current filters")){

        }
        else if(command.startsWith("disable filter ")){

        }
        else if(command.equals("sorting")){

        }
        else if(command.equals("show available sorts")){

        }
        else if(command.startsWith("sort ")){

        }
        else if(command.equals("current sort")){

        }
        else if(command.equals("disable sort")){

        }
        else if(command.equals("show products")){

        }
        else if(command.startsWith("show product ")){
            showProduct(command);
        }
        else if(command.equals("help")){
            help();
        }
        else if(command.equals("back")){
            View.setCurrentMenu(View.previousMenu);
        }
        else {
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
        }
    }

    @Override
    public void help(){
        System.out.println(View.ANSI_YELLOW+"You are in the shop menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view categories");
        System.out.println("filtering");
        System.out.println("show available filters");
        System.out.println("filter [an available filter]");
        System.out.println("current filters");
        System.out.println("disable filter [a selected filter]");
        System.out.println("sorting");
        System.out.println("show available sorts");
        System.out.println("sort [an available sort]");
        System.out.println("current sort");
        System.out.println("disable sort");
        System.out.println("show products");
        System.out.println("show product [product id]");
        System.out.println("back");
    }


    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void cart(){

    }

    public void viewCategories(){

    }

    public void filtering(){

    }

    public void showAvailableFilters(){

    }

    public void currentFilters(){

    }

    public void disableFilter(){

    }

    public void sorting() {

    }

    public void showAvailableSorts(){

    }

    public void sort(){

    }

    public void currentSort(){

    }

    public void disableSort(){

    }

    public void showProducts(){

    }

    public void showProduct(String command){
        if(command.split(" ").length != 3){
            System.out.println(View.ANSI_RED+"Invalid ID."+View.ANSI_RESET);
            return;
        }
        String itemID = command.split(" ")[2];
        if(!ItemAndCategoryController.getInstance().currentViewableItemsContainsItem(itemID)){
            System.out.println(View.ANSI_RED+"Invalid ID."+View.ANSI_RESET);
            return;
        }

        ItemMenu.getInstance().setItemID(itemID);
        View.setCurrentMenu(ItemMenu.getInstance());
    }

}
