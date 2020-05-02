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
    public void run(){
        help();
        String command = View.read.nextLine();
        execute(command);
    }

   /* @Override
    public void show(){
        help();
        String command = View.read.nextLine();
        execute(command);
    }*/


    @Override
    public void execute(String command){
        if(command.startsWith("create account ")){
            register(command);
        }
        else if(command.startsWith("login ")){
            if(login(command)){
                View.setCurrentMenu(View.previousMenu);
            }
        }
        else if(command.equals("help")){
            help();
        }
        else if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
        }
        else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            run();
        }
    }

    @Override
    public void help(){
        System.out.println(View.ANSI_RED+"Log in or Register.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("create account [type] [username]");
        System.out.println(View.ANSI_WHITE +"where type is buyer/seller/admin and username cannot contain spaces."+View.ANSI_RESET);
        System.out.println("login [username]");
        System.out.println("back");
    }

}
