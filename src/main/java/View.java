import java.util.Scanner;
public class View {
    private static Scanner read = new Scanner(System.in);
    private static boolean programRunning=true;
    private static Menu currentMenu;

    public static void readCommand(){
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



}
