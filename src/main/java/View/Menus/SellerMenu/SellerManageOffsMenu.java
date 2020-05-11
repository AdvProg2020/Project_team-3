package View.Menus.SellerMenu;

import View.Menus.UserMenu;
import View.Menus.View;

public class SellerManageOffsMenu extends UserMenu {
    private static SellerManageOffsMenu sellerManageOffsMenu;
    private SellerManageOffsMenu(){ }

    public static SellerManageOffsMenu getInstance(){
        if(sellerManageOffsMenu==null)
            sellerManageOffsMenu = new SellerManageOffsMenu();
        return sellerManageOffsMenu;
    }

    public void run(){
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }
    @Override
    public void execute(String command) {
        if(command.equals("view offs")){
            viewAllOffs();
        }
        if(command.startsWith("view off ")){
            viewOff(command);
        }
        if(command.startsWith("edit off ")){
            editSale();
        }
        if(command.equals("add off")){
            addSale();
        }
        if(command.equals("back")){
            View.setCurrentMenu(SellerMenu.getInstance());
        }
        if(command.equals("help")){
            help();
        }
    }
    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the seller menu."+View.ANSI_RESET);
        System.out.println("view offs");
        //System.out.println("view off [offId]");
        //System.out.println("edit off [offId]");
        //System.out.println("add off");
    }

    private void viewAllOffs(){

    }

    public void viewOff(String command){

    }



    public void editSale(){

    }

    public void addSale(){

    }
}
