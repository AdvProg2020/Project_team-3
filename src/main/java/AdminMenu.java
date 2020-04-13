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
        System.out.println("1-Edit personal information");
        System.out.println("2-See requests");
        System.out.println("3-Add admin account");
        System.out.println("4-Show sales");
        System.out.println("5-Add special sale");
        System.out.println("6-Delete user");
        System.out.println("7-See all categories");
        System.out.println("8-Add category");
        System.out.println("9-Logout");
    }

    @Override
    public String toString(){
        return "1-Edit personal information\n2-See requests\n3-Add admin account\n4-Show sales\n5-Add special sale\n6-Delete user\n7-See all categories\n8-Add category\n9-Logout";
    }
    @Override
    public void execute(String command){

    }
}
