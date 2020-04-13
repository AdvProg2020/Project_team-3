import java.util.Scanner;
public class View {
    private static Scanner read = new Scanner(System.in);
    private static boolean programRunning=true;
    public static void readCommand(){
        String command;
        while(programRunning){
            command = read.nextLine();
            Controller.getInstance().processCommand(command);
        }
    }

    public static void setProgramRunning(boolean programRunning) {
        View.programRunning = programRunning;
    }
}
