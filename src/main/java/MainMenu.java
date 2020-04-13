public class MainMenu {
    private static MainMenu mainMenu;
    private MainMenu(){ }

    public static MainMenu getInstance(){
        if(mainMenu==null)
            mainMenu = new MainMenu();
        return mainMenu;
    }

    public void show(){
        System.out.println("1-Login");
        System.out.println("2-Register");
        System.out.println("3-Shop");
        System.out.println("4-exit");
    }

    @Override
    public String toString(){
        return "1-Login\n2-Register\n3-Shop\n4-exit";
    }

    public void execute(){

    }

}
