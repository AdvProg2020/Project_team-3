package View.Menus;
import Controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    static Scanner read = new Scanner(System.in);  //package access
    private static boolean programRunning = true;
    private static Menu currentMenu = MainMenu.getInstance();

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
        System.out.println(ANSI_YELLOW + "Team 3 MVC Shop Project, Phase 1 ..." + ANSI_RESET);
        while (programRunning) {
            Controller.getInstance().updateDateAndTime();
            currentMenu.run();
        }
    }

    public static void setProgramRunning(boolean programRunning) {
        View.programRunning = programRunning;
    }

    public static void setCurrentMenu(Menu menu) {
        currentMenu = menu;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static Matcher getMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static Scanner getRead() {
        return read;
    }


    public static void setFonts(AnchorPane pane){
        for (Node child : pane.getChildren()) {
            if(child instanceof Label)
                ((Label) child).setFont(Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
            if(child instanceof TextField)
                ((TextField) child).setFont(Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
            if(child instanceof TextArea)
                ((TextArea) child).setFont(Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
            if(child instanceof Text)
                ((Text) child).setFont(Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
        }
    }

}
