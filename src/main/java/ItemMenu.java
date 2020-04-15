public class ItemMenu extends Menu {
    private static ItemMenu itemMenu;
    private int optionCount = 4;
    private ItemMenu(){ }

    public static ItemMenu getInstance(){
        if(itemMenu==null)
            itemMenu = new ItemMenu();
        return itemMenu;
    }
    @Override
    public void show(){

    }

    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }

    public void digest(){

    }

    public void addToCart(){

    }

    public void selectSeller(){

    }

    public void attributes(){

    }

    public void compare(){

    }

    public void comments(){

    }

    public void addComment(){

    }

}
