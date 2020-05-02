package View.Menus;

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

    }

    @Override
    public void execute(String command) {

    }

  /*  @Override
    public void show(){

    }



    @Override
    public void execute(String command){

    } */


    @Override
    public void help(){

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

    public void showProduct(){

    }

}
