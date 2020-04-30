import Control.LoadGson;
import View.Menus.View;

public class Main {
    public static void main (String[] args) {
        try{
            LoadGson.loadFromDataBase();
        }catch (Exception e){
            System.out.println("kyaaaa");
        }
        View.run();
    }
}
