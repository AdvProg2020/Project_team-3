import java.util.ArrayList;

public class Controller {
    private static Controller controller;

    private Admin admin;
    private ArrayList <User> allUsers = new ArrayList<>();
    private ArrayList <Sale> allSales = new ArrayList<>();
    private ArrayList <DiscountCode> allDiscountCodes = new ArrayList<>();
    private ArrayList <Item> allItems = new ArrayList<>();
    private User currentOnlineUser;
    private Category mainCategory;
    private Category currentCategory;
    private Cart currentShoppingCart;

    private Controller(){

    }
    public static Controller getInstance(){
        if(controller==null)
            controller = new Controller();
        return controller;
    }

    public User getUserByUsername (String username){
        if(allUsers.isEmpty()) return null;
        for(User user:allUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

}