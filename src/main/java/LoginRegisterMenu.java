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
    public String toString(){
        return "";
    }
    @Override
    public void execute(String command){

    }
    @Override
    public void login(){

    }

    @Override
    public void register(){

    }
    @Override
    public void logout(){

    }
    @Override
    public void help(){

    }
}
