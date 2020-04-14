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
    public String toString(){
        return "";
    }
    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }

}
