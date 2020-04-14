import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    String username;
    HashMap<String,Integer> allItemName;

    Cart(String username){
        this.username=username;
    }

    public void add(String itemName){

    }

    public void remove(String itemName){

    }

    public void changeCountBy(String itemName,int count){

    }

    public String getUsername() {
        return username;
    }

    public boolean includesItem(String itemID){
        return false;
    }

    public int getItemCount(String itemID){
        return 5;
    }

    Cart empty(){
       return null;
    }
}
