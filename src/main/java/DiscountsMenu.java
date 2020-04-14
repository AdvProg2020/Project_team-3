public class DiscountsMenu extends  Menu {
    private static DiscountsMenu discountsMenu;
    private int optionCount = 6;
    private DiscountsMenu(){ }

    public static DiscountsMenu getInstance(){
        if(discountsMenu==null)
            discountsMenu = new DiscountsMenu();
        return discountsMenu;
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
