import Control.Database;
import View.Menus.View;

public class Main {
    public static void main (String[] args)  {
        Database.initiate();
        View.run();
    }
}
