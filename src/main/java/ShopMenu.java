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
    public void show(){

    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public void execute(String command){

    }
    @Override
    public void login(){

    }

    @Override
    public void register(){

    }

    @Override
    public void help(){

    }

    @Override
    public void logout(){

    }

    @Override
    public int getOptionCount() {
        return optionCount;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }
}
