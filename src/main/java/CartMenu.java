public class CartMenu extends Menu {
    private static CartMenu cartMenu;
    private int optionCount = 4;
    private CartMenu(){ }

    public static CartMenu getInstance(){
        if(cartMenu==null)
            cartMenu = new CartMenu();
        return cartMenu;
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
}

