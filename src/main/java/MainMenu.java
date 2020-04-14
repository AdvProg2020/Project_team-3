public class MainMenu extends Menu{
    private static MainMenu mainMenu;
    private int optionCount = 4;
    private MainMenu(){ }

    public static MainMenu getInstance(){
        if(mainMenu==null)
            mainMenu = new MainMenu();
        return mainMenu;
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
