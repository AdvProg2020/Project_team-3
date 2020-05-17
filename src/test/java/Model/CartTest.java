package Model;

import Controller.CartController;
import Controller.ItemAndCategoryController;
import Controller.RequestController;
import Controller.UserController;
import Model.Requests.Request;
import Model.Users.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CartTest {
    public static void addItems() {
        UserController.getInstance().registerSeller(500,"testSeller","1234",
                "test","test","alireza@gmail.com","33824264","benz");
        UserController.getInstance().registerBuyer(50000,"testBuyer","1234",
                "test","test","alireza@gmail.com","33824264");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("testSeller");
        System.out.println(UserController.getInstance().login(seller.getUsername(),seller.getPassword()));
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        ItemAndCategoryController.getInstance().addCategory("testSort",null,"Main");
        ItemAndCategoryController.getInstance().addItem("Vacuum345search","Benz"
                ,"this is vaccum",500,10,"testSort",
                attributes);
        ArrayList<Request> allRequests= RequestController.getInstance().getAllRequestFromDataBase();
        acceptRequests();
    }

    public static void acceptRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    public static void deleteJunk(){
        UserController.getInstance().login("admin","12345");
        System.out.println(UserController.getInstance().deleteUser("testBuyer"));
        System.out.println(UserController.getInstance().deleteUser("testSeller"));
        ItemAndCategoryController.getInstance().removeCategory("testSort");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        UserController.getInstance().logout();
    }


    @Test
    public void buy() {
        addItems();
        ArrayList<Item> allItemsId=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Cart cart=CartController.getInstance().getCurrentShoppingCart();
        String id="";
        for (Item item : allItemsId) {
            if(item.getSellerName().equals("testSeller")){
                id=item.getId();
            }
        }
        cart.add(id);
        cart.changeCountBy(id,2);
        cart.buy("testBuyer","testaddress");
        deleteJunk();
    }
}