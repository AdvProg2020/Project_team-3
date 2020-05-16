package View.Menus.SellerMenu;

import Controller.SaleAndDiscountCodeController;
import Controller.UserController;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;

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
        Matcher matcher;
        if(command.equals("view all offs")){
            viewAllOffs();
            return;
        }
        if(command.startsWith("view off ")){
            matcher=View.getMatcher("view off (\\S+)",command);
            if(matcher.matches()){
                viewOff(matcher.group(1));
            }
            return;
        }
        if(command.startsWith("edit off ")){
            matcher=View.getMatcher("edit off (\\S+)",command);
            if(matcher.matches()){
                editSale(matcher.group(1));
            }
            return;
        }
        if(command.equals("add off")){
            addSale();
            return;
        }
        if(command.equals("back")){
            View.setCurrentMenu(SellerMenu.getInstance());
            return;
        }
        if(command.equals("help")){
            help();
            return;
        }
        if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }

        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);

    }
    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the seller menu."+View.ANSI_RESET);
        System.out.println("view all offs");
        System.out.println("view off [offId]");
        System.out.println("edit off [offId]");
        System.out.println("add off");
    }

    protected void viewAllOffs(){
        System.out.println(SaleAndDiscountCodeController.getInstance().getSellerSalesString(UserController.getInstance().getCurrentOnlineUser().getUsername()));
    }

    protected void viewOff(String offID){
        if(!SaleAndDiscountCodeController.getInstance().isThereSaleWithId(offID)){
            System.out.println(View.ANSI_RED+"No sale with this ID"+View.ANSI_RESET);
            return;
        }
        if(!SaleAndDiscountCodeController.getInstance().getSaleById(offID).getSellerUsername().equals(UserController.getInstance().getCurrentOnlineUser().getUsername())){
            System.out.println(View.ANSI_RED+"This sale does not belong to you."+View.ANSI_RESET);
            return;
        }
        System.out.println(SaleAndDiscountCodeController.getInstance().getSaleById(offID).toString());
    }


    protected void editSale(String saleID){
        if(!SaleAndDiscountCodeController.getInstance().getSaleById(saleID).getSellerUsername().equals(UserController.getInstance().getCurrentOnlineUser().getUsername())){
            System.out.println(View.ANSI_RED+"This sale does not belong to you."+View.ANSI_RESET);
            return;
        }
        System.out.println("Enter -edit [start/end] date- if you wish to change the starting/ending date.\nEnter -edit offpercent- if you wish to change the off percentage.");
        String command = View.getRead().nextLine();
        if(command.equals("edit end date")){
            LocalDateTime date = getDate("Enter a valid day as the end time in the following format: dd/MM/yyyy HH:mm");
            String dateString = date.toString();
            System.out.println(SaleAndDiscountCodeController.getInstance().editSale(saleID,"end time",dateString));
            return;
        }
        else if(command.equals("edit start date")){
            LocalDateTime date = getDate("Enter a valid day as the start time in the following format: dd/MM/yyyy HH:mm");
            String dateString = date.toString();
            System.out.println(SaleAndDiscountCodeController.getInstance().editSale(saleID,"start time",dateString));
            return;
        }
        else if(command.equals("edit offpercent")){
            int percentage = readNumber(101, "please enter new discount percentage:");
            System.out.println(SaleAndDiscountCodeController.getInstance().editSale(saleID,"off percentage",Integer.toString(percentage)));
            return;
        }
        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);

    }

    protected void addSale(){
        int percentage = readNumber(101, "Please enter sale percentage:");
        LocalDateTime startTime = getDate("Enter a valid day as the starting time in the following format: dd/MM/yyyy HH:mm");
        LocalDateTime endTime = getDate("Enter a valid day as the end time in the following format: dd/MM/yyyy HH:mm");

        System.out.println("What items (ID) do you want to add? Type done to finish.");
        String itemID;
        ArrayList<String> addedItems = new ArrayList<>();
        while(true){
            itemID = View.getRead().nextLine();
            if(itemID.equals("done")) break;
            if(!SaleAndDiscountCodeController.getInstance().canAddItemToSale(itemID)){
                System.out.println(View.ANSI_RED+"Error:invalid item!"+View.ANSI_RESET);
                continue;
            }
            addedItems.add(itemID);
        }

        System.out.println(SaleAndDiscountCodeController.getInstance().addSale(startTime,endTime,percentage,addedItems));
    }
}
