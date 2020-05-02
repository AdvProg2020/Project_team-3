package View.Menus;

import Control.Controller;
import Control.UserController;
import Model.Users.Admin;
import Model.Users.Seller;
import Model.Users.User;

public class ViewPersonalInfo extends UserMenu {
    private static ViewPersonalInfo viewPersonalInfo ;
    private int optionCount = 4;
    private String username ;
    private User user ;
    private ViewPersonalInfo(){ }

    public static ViewPersonalInfo getInstance(){
        if(viewPersonalInfo==null)
            viewPersonalInfo = new ViewPersonalInfo();
        return viewPersonalInfo;
    }

    @Override
    public void run(){
        username = UserController.getInstance().getCurrentOnlineUser().getUsername();
        user = UserController.getInstance().getUserByUsername(username);
        System.out.print(UserController.getInstance().viewPersonalInfo(Controller.getInstance().getCurrentOnlineUser().getUsername()));
        System.out.println(View.ANSI_PURPLE+"type \"edit [field]\" to edit a field or \"back\" to go back."+View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if(command.startsWith("edit") && command.split(" ").length==2){
            editPersonalInfo(command);
        }
        else if(command.equals("help")){
            help();
        }
        else if(command.equals("back")){
            back();
        }
        else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
        }
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_PURPLE+"type \"edit [field]\" to edit a field or \"back\" to go back."+View.ANSI_RESET);
    }

    public void editPersonalInfo(String command){
        String field = command.split(" ")[1];
        if(field.equals("Name")){
            editName();
        }
        else if(field.equals("Surname")){
            editLastName();
        }
        else if(field.equals("Email")){
            editEmail();
        }
        else if(field.equals("Number")){
            editNumber();
        }
        else if(field.equals("Company") && user instanceof Seller){
            editCompanyName();
        }
        else{
            System.out.println(View.ANSI_RED+"You cannot edit that field or it does not exist."+View.ANSI_RESET);
        }
    }

    public void editName(){
        System.out.print("Enter your new name:");
        String name = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username,"Name",name);
    }

    public void editLastName(){
        System.out.print("Enter your new surname:");
        String surname = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username,"Surname",surname);
    }

    public void editEmail(){
        String email = enterEmail();
        UserController.getInstance().editPersonalInfo(username,"Email",email);
    }

    public void editNumber(){
        String number = enterNumber();
        UserController.getInstance().editPersonalInfo(username,"Number",number);
    }

    public void editCompanyName(){
        System.out.print("Enter your new company name:");
        String name = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username,"CompanyName",name);
    }

    public void back(){
        if(UserController.getInstance().getCurrentOnlineUser() instanceof Admin){
            View.setCurrentMenu(AdminMenu.getInstance());
        }else if(UserController.getInstance().getCurrentOnlineUser() instanceof Seller){
            View.setCurrentMenu(SellerMenu.getInstance());
        }else{
            View.setCurrentMenu(BuyerMenu.getInstance());
        }
    }

  /*  @Override
    public void show() {
        help();
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if(command.equals("E")){

        }else if(command.equals("X")){
            if(UserController.getInstance().getCurrentOnlineUser() == null){
                LoginRegisterMenu.getInstance().show();
            }
            if(UserController.getInstance().getCurrentOnlineUser() instanceof Admin){
                AdminMenu.getInstance().show();
            }else if(UserController.getInstance().getCurrentOnlineUser() instanceof Seller){
                SellerMenu.getInstance().show();
            }else{
                BuyerMenu.getInstance().show();
            }
        }else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            show();
        }
    } */

}
