package View.Menus;

import java.util.Scanner;

public class View {
    static Scanner read = new Scanner(System.in);  //package access
    private static boolean programRunning = true;
    private static Menu currentMenu=MainMenu.getInstance();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void run() {
        System.out.println(ANSI_WHITE+"Team 3 MVC Shop Project, Phase 1 ..."+ANSI_RESET);
        while(programRunning){
            currentMenu.run();
        }
    }

    public static void setProgramRunning(boolean programRunning) {
        View.programRunning = programRunning;
    }

    public static void setCurrentMenu(Menu menu) {
       currentMenu=menu;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }


}
