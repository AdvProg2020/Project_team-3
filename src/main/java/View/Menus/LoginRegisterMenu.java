package View.Menus;

public class LoginRegisterMenu extends Menu {
    private static LoginRegisterMenu loginRegisterMenu;
    private int optionCount = 4;
    private LoginRegisterMenu(){ }

    public static LoginRegisterMenu getInstance(){
        if(loginRegisterMenu==null)
            loginRegisterMenu = new LoginRegisterMenu();
        return loginRegisterMenu;
    }
    @Override
    public void show(){
        help();
        String command = View.read.nextLine();
        execute(command);
    }


    @Override
    public void execute(String command){
        if(command.equals("1")){
            if(!login()) show();
        }
        else if(command.equals("2")){
            register();
            show();
        }
        else if(command.equals("3")){
            show();
        }
        else {
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            show();
        }
    }

    @Override
    public void help(){
        System.out.println(View.ANSI_RED+"You must be logged in to continue."+View.ANSI_RESET);
        System.out.println("1- Login \n2- Register\n3- Help");
    }

}
