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

    private Controller(){ }

    public static Controller getInstance(){
        if(controller==null)
            controller = new Controller();
        return controller;
    }

    public void initiate(){   //should be called to initiate saved Gsons

    }

    public User getUserByUsername (String username){
        for(User user:allUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean isThereUserByName(String name){
        for (User user : allUsers) {
            if(user.getUsername().equals(name)){
                return true;
            }
        }
              return false;
    }

    public Sale getSaleById(String id){
        for (Sale sale : allSales) {
            if(sale.getId().equals(id)){
                return sale;
            }
        }
               return null;
    }

    public Item getItemById(String id){
        for (Item item : allItems) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
                return null;
    }

    public DiscountCode getDiscountCodeById(String id){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountId().equals(id)){
                return discountCode;
            }
        }
               return null;
    }
}