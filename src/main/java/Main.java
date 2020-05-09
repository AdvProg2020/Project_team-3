import Control.Database;
import View.Menus.View;

public class Main {
    public static void main (String[] args)  {
        Database.getInstance().initiate();
        View.run();
    }
}
