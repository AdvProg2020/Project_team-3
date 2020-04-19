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

    }


    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }

}
