import java.util.ArrayList;

public class AdminMenu extends Menu{
    private static AdminMenu adminMenu;
    private int optionCount = 9;
    private AdminMenu(){ }

    public static AdminMenu getInstance(){
        if(adminMenu==null)
            adminMenu = new AdminMenu();
        return adminMenu;
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
