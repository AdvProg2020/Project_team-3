package View.Menus;

public class DiscountsMenu extends Menu {
    private static DiscountsMenu discountsMenu;
    private int optionCount = 6;
    private DiscountsMenu(){ }

    public static DiscountsMenu getInstance(){
        if(discountsMenu==null)
            discountsMenu = new DiscountsMenu();
        return discountsMenu;
    }

    @Override
    public void run(){
        System.out.println(View.ANSI_BLUE + "You are in the Discount menu." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command){
     if(command.equals("offs")){

     }
    }

    @Override
    public void help(){
    System.out.println("offs");
    System.out.println("show product [product id]");
    }

    public void offs(){

    }

    public void showProduct(){

    }



}
