import java.util.Scanner;
public class View {
    static Scanner read = new Scanner(System.in);  //package access
    private static boolean programRunning=true;
    private static Menu currentMenu;

    public static void run(){
        String command;
        while(programRunning){
            command = read.nextLine();
            currentMenu.execute(command);
        }
    }

    public static void setProgramRunning(boolean programRunning) {
        View.programRunning = programRunning;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }
}
